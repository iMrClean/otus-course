package ru.otus.course.crm.service;

import ru.otus.course.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

  Client saveClient(Client client);

  Optional<Client> getClient(Long id);

  List<Client> findAll();

}
