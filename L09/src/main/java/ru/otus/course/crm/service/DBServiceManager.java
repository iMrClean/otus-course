package ru.otus.course.crm.service;

import ru.otus.course.crm.model.Manager;

import java.util.List;
import java.util.Optional;

public interface DBServiceManager {

  Manager saveManager(Manager client);

  Optional<Manager> getManager(Long no);

  List<Manager> findAll();

}
