package ru.otus.course.base;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.otus.course.core.repository.DataTemplateHibernate;
import ru.otus.course.core.repository.HibernateUtils;
import ru.otus.course.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.course.crm.model.Address;
import ru.otus.course.crm.model.Client;
import ru.otus.course.crm.model.Phone;
import ru.otus.course.crm.service.DBServiceClient;
import ru.otus.course.crm.service.DBServiceClientImpl;

import static ru.otus.course.Main.HIBERNATE_CFG_FILE;
import static ru.otus.course.crm.migration.MigrationsExecutorFlyway.createMigrationsExecutorFlyway;

public abstract class AbstractHibernateTest {

  protected SessionFactory sessionFactory;

  protected TransactionManagerHibernate transactionManager;

  protected DataTemplateHibernate<Client> clientTemplate;

  protected DBServiceClient dbServiceClient;

  private static TestContainersConfig.CustomPostgreSQLContainer CONTAINER;

  @BeforeAll
  public static void init() {
    CONTAINER = TestContainersConfig.CustomPostgreSQLContainer.getInstance();
    CONTAINER.start();
  }

  @AfterAll
  public static void shutdown() {
    CONTAINER.stop();
  }

  @BeforeEach
  public void setUp() {
    String url = System.getProperty("app.datasource.demo-db.jdbcUrl");
    String username = System.getProperty("app.datasource.demo-db.username");
    String password = System.getProperty("app.datasource.demo-db.password");

    var migrationsExecutorFlyway = createMigrationsExecutorFlyway(url, username, password);
    migrationsExecutorFlyway.executeMigrations();

    Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
    configuration.setProperty("hibernate.connection.url", url);
    configuration.setProperty("hibernate.connection.username", username);
    configuration.setProperty("hibernate.connection.password", password);

    sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

    transactionManager = new TransactionManagerHibernate(sessionFactory);
    clientTemplate = new DataTemplateHibernate<>(Client.class);
    dbServiceClient = new DBServiceClientImpl(transactionManager, clientTemplate);
  }

  protected EntityStatistics getUsageStatistics() {
    Statistics stats = sessionFactory.getStatistics();
    return stats.getEntityStatistics(Client.class.getName());
  }

}
