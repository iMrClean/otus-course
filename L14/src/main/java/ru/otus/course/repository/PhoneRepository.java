package ru.otus.course.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.course.domain.Phone;

public interface PhoneRepository extends ListCrudRepository<Phone, Long> { }
