package ru.otus.course;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;
import ru.otus.course.core.repository.DataTemplateHibernate;
import ru.otus.course.core.repository.HibernateUtils;
import ru.otus.course.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.course.crm.model.Address;
import ru.otus.course.crm.model.Client;
import ru.otus.course.crm.model.Phone;
import ru.otus.course.crm.service.DBServiceClientImpl;

import static ru.otus.course.crm.migration.MigrationsExecutorFlyway.createMigrationsExecutorFlyway;

@Slf4j
public class Main {

  public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

  public static void main(String[] args) {
    var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

    var url = configuration.getProperty("hibernate.connection.url");
    var username = configuration.getProperty("hibernate.connection.username");
    var password = configuration.getProperty("hibernate.connection.password");

    var migrationsExecutorFlyway = createMigrationsExecutorFlyway(url, username, password);
    migrationsExecutorFlyway.executeMigrations();

    var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

    var transactionManager = new TransactionManagerHibernate(sessionFactory);

    var clientTemplate = new DataTemplateHibernate<>(Client.class);

    var dbServiceClient = new DBServiceClientImpl(transactionManager, clientTemplate);

    processingClient(dbServiceClient);
  }

  private static void processingClient(DBServiceClientImpl dbServiceClient) {
    var clientFirst = dbServiceClient.saveClient(new Client("dbServiceFirst"));
    log.info("clientFirst:{}", clientFirst);

    var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
    log.info("clientSecond:{}", clientSecond);

    var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId()).orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
    log.info("clientSecondSelected:{}", clientSecondSelected);

    var clientSecondUpdated = dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated"));
    log.info("clientSecondUpdated:{}", clientSecondUpdated);

    dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
  }

}
