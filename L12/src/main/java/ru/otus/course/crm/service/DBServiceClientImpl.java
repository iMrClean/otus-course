package ru.otus.course.crm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import ru.otus.course.core.repository.DataTemplate;
import ru.otus.course.core.sessionmanager.TransactionManager;
import ru.otus.course.crm.model.Client;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class DBServiceClientImpl implements DBServiceClient {

  private final TransactionManager transactionManager;

  private final DataTemplate<Client> clientDataTemplate;

  @Override
  public Client saveClient(Client client) {
    return transactionManager.doInTransaction(session -> {
      var clientClone = client.clone();

      return saveOrUpdateClient(session, clientClone);
    });
  }

  @Override
  public Optional<Client> getClient(Long id) {
    return transactionManager.doInReadOnlyTransaction(session -> {
      var client = clientDataTemplate.findById(session, id);
      log.info("client: {}", client);

      return client;
    });
  }

  @Override
  public List<Client> findAll() {
    return transactionManager.doInReadOnlyTransaction(session -> {
      var clients = clientDataTemplate.findAll(session);
      log.info("clients: {}", clients);

      return clients;
    });
  }

  private Client saveOrUpdateClient(Session session, Client client) {
    Client result;
    if (client.getId() == null) {
      result = clientDataTemplate.insert(session, client);
      log.info("Created client: {}", result);
    } else {
      result = clientDataTemplate.update(session, client);
      log.info("Updated client: {}", result);
    }

    return result;
  }

}
