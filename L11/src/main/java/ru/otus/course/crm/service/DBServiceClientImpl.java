package ru.otus.course.crm.service;

import lombok.extern.slf4j.Slf4j;
import ru.otus.course.cache.HwCache;
import ru.otus.course.cache.HwListener;
import ru.otus.course.core.repository.DataTemplate;
import ru.otus.course.core.sessionmanager.TransactionManager;
import ru.otus.course.crm.model.Client;

import java.util.List;
import java.util.Optional;

@Slf4j
public class DBServiceClientImpl implements DBServiceClient {

  private final TransactionManager transactionManager;

  private final DataTemplate<Client> clientDataTemplate;

  private final HwCache<String, Client> clientCaches;

  public DBServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, HwCache<String, Client> clientCaches) {
    this.transactionManager = transactionManager;
    this.clientDataTemplate = clientDataTemplate;
    this.clientCaches = clientCaches;
    HwListener<String, Client> listener = new HwListener<String, Client>() {
      @Override
      public void notify(String key, Client client, String action) {
        log.info("cache: client.id:{}, action:{}", key, action);
      }
    };

    clientCaches.addListener(listener);
  }

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
      clientCaches.put(savedClient.getId().toString(), savedClient);

      return savedClient;
    });
  }

  @Override
  public Optional<Client> getClient(Long id) {
    var clientCache = clientCaches.get(String.valueOf(id));
    if (clientCache != null) {
      return Optional.of(clientCache);
    }
    return transactionManager.doInReadOnlyTransaction(session -> {
      var clientOptional = clientDataTemplate.findById(session, id);
      log.info("client: {}", clientOptional);
      clientOptional.ifPresent(client -> clientCaches.put(client.getId().toString(), client));

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
