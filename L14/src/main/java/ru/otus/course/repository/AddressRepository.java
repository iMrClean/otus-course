package ru.otus.course.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.course.domain.Address;

public interface AddressRepository extends ListCrudRepository<Address, Long> { }
