package ru.otus.course;

import ru.otus.course.core.config.HibernateConfiguration;
import ru.otus.course.core.repository.DataTemplateHibernate;
import ru.otus.course.core.repository.UserDataTemplateImpl;
import ru.otus.course.core.sessionmanager.HibernateTransactionManager;
import ru.otus.course.crm.model.Client;
import ru.otus.course.crm.service.DBServiceClientImpl;
import ru.otus.course.processor.TemplateProcessorImpl;
import ru.otus.course.server.DefaultUserWebServer;
import ru.otus.course.service.LoginServiceImpl;

import static ru.otus.course.crm.migration.MigrationsExecutorFlyway.createMigrationsExecutorFlyway;

public class Main {

  /**
   * Стартовая страница http://localhost:8080
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    var configuration = HibernateConfiguration.createHibernateConfiguration();

    createMigrationsExecutorFlyway(configuration).executeMigrations();

    createWebServer(configuration);
  }

  private static void createWebServer(HibernateConfiguration configuration) throws Exception {
    var sessionFactory = configuration.buildSessionFactory();
    var transactionManager = new HibernateTransactionManager(sessionFactory);

    var clientTemplate = new DataTemplateHibernate<>(Client.class);
    var serviceClient = new DBServiceClientImpl(transactionManager, clientTemplate);
    var userDataTemplate = new UserDataTemplateImpl();
    var loginService = new LoginServiceImpl(transactionManager, userDataTemplate);
    var templateProcessor = new TemplateProcessorImpl();

    var usersWebServer = new DefaultUserWebServer(templateProcessor, loginService, serviceClient);
    usersWebServer.start();
  }

}
