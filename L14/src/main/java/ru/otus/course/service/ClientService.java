package ru.otus.course.service;

import ru.otus.course.domain.Client;

import java.util.List;

public interface ClientService {

  List<Client> findAll();

  Client save(Client client);

  Client findById(Long id);

  void deleteById(Long id);

}
