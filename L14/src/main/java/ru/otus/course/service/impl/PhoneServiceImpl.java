package ru.otus.course.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.course.repository.PhoneRepository;
import ru.otus.course.service.PhoneService;
import ru.otus.course.transaction.TransactionManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

  private final PhoneRepository phoneRepository;

  private final TransactionManager transactionManager;

}
