package ru.otus.course.core.repository;

import org.hibernate.Session;
import ru.otus.course.crm.model.SecUser;

import java.util.Optional;

public interface UserDataTemplate {

  Optional<SecUser> findByLogin(Session session, String login);

}
