package ru.otus.course.core.repository;

import org.hibernate.Session;
import ru.otus.course.crm.model.SecUser;

import java.util.Optional;

public class UserDataTemplateImpl implements UserDataTemplate {

  @Override
  public Optional<SecUser> findByLogin(Session session, String login) {
    var query = session.createQuery("select u from SecUser u where u.login = :login", SecUser.class);

    query.setParameter("login", login);

    return Optional.ofNullable(query.getResultList().getFirst());
  }

}
