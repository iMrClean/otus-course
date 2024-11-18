package ru.otus.course.core.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.course.core.util.HibernateUtils;
import ru.otus.course.crm.model.Address;
import ru.otus.course.crm.model.Client;
import ru.otus.course.crm.model.Phone;
import ru.otus.course.crm.model.SecUser;

public class HibernateConfiguration {

  private final Configuration configuration;

  private final Class<?>[] annotatedClasses;

  public static HibernateConfiguration createHibernateConfiguration() {
    return new HibernateConfiguration();
  }

  private HibernateConfiguration() {
    this.configuration = new Configuration().configure();
    this.annotatedClasses = new Class<?>[]{Address.class, Client.class, Phone.class, SecUser.class};
  }

  public String getUrl() {
    return configuration.getProperty("hibernate.connection.url");
  }

  public String getUsername() {
    return configuration.getProperty("hibernate.connection.username");
  }

  public String getPassword() {
    return configuration.getProperty("hibernate.connection.password");
  }

  public SessionFactory buildSessionFactory() {
    return HibernateUtils.buildSessionFactory(configuration, annotatedClasses);
  }

}
