package ru.otus.course.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.course.repository.AddressRepository;
import ru.otus.course.service.AddressService;
import ru.otus.course.transaction.TransactionManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  private final TransactionManager transactionManager;

}
