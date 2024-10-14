package ru.otus.course.crm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
      var clientCloned = client.clone();
      if (client.getId() == null) {
        var savedClient = clientDataTemplate.insert(session, clientCloned);
        log.info("created client: {}", clientCloned);

        return savedClient;
      }
      var savedClient = clientDataTemplate.update(session, clientCloned);
      log.info("updated client: {}", savedClient);

      return savedClient;
    });
  }

  @Override
  public Optional<Client> getClient(Long id) {
    return transactionManager.doInReadOnlyTransaction(session -> {
      var clientOptional = clientDataTemplate.findById(session, id);
      log.info("client: {}", clientOptional);

      return clientOptional;
    });
  }

  @Override
  public List<Client> findAll() {
    return transactionManager.doInReadOnlyTransaction(session -> {
      var clientList = clientDataTemplate.findAll(session);
      log.info("clientList:{}", clientList);

      return clientList;
    });
  }

}
