package ru.otus.course.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.course.domain.Client;
import ru.otus.course.repository.ClientRepository;
import ru.otus.course.service.ClientService;
import ru.otus.course.transaction.TransactionManager;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;

  private final TransactionManager transactionManager;

  @Override
  public List<Client> findAll() {
    return clientRepository.findAllByCustomQuery();
  }

  @Override
  @Transactional
  public Client save(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public Client findById(Long id) {
    return clientRepository.findById(id).orElseThrow();
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    clientRepository.deleteById(id);
  }

}
