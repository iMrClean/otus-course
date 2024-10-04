package ru.otus.course;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.course.core.repository.executor.DbExecutorImpl;
import ru.otus.course.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.course.crm.datasource.DriverManagerDataSource;
import ru.otus.course.crm.model.Client;
import ru.otus.course.crm.model.Manager;
import ru.otus.course.crm.service.DbServiceClientImpl;
import ru.otus.course.crm.service.DbServiceManagerImpl;
import ru.otus.course.jdbc.mapper.*;

import javax.sql.DataSource;
import java.util.List;

public class Main {

  private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
  private static final String USER = "postgres";
  private static final String PASSWORD = "postgres";

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    var dataSource = createDataSourceConnection();
    flywayMigrations(dataSource);

    var dbExecutor = new DbExecutorImpl();
    var transactionRunner = new TransactionRunnerJdbc(dataSource);

    processingClient(dbExecutor, transactionRunner);

    processingManager(dbExecutor, transactionRunner);
  }

  private static DriverManagerDataSource createDataSourceConnection() {
    return new DriverManagerDataSource(URL, USER, PASSWORD);
  }

  private static void flywayMigrations(DataSource dataSource) {
    log.info("db migration started...");
    var flyway = Flyway.configure()
      .dataSource(dataSource)
      .locations("classpath:/db/migration")
      .load();
    flyway.migrate();
    log.info("db migration finished.");
    log.info("***");
  }

  private static void processingClient(DbExecutorImpl dbExecutor, TransactionRunnerJdbc transactionRunner) {
    var entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
    var entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient);
    var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, entityClassMetaDataClient, entitySQLMetaDataClient);

    var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
    dbServiceClient.saveClient(new Client("dbServiceFirst"));

    var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
    var clientSecondSelected = dbServiceClient
      .getClient(clientSecond.getId())
      .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
    log.info("clientSecondSelected:{}", clientSecondSelected);
    clientSecondSelected.setName("UpdatedService");

    dbServiceClient.saveClient(clientSecondSelected);
    log.info("updated clientSecondSelected:{}", clientSecondSelected);

    var allClients = dbServiceClient.findAll();
    log.info("Clients:{}", allClients);
  }

  private static void processingManager(DbExecutorImpl dbExecutor, TransactionRunnerJdbc transactionRunner) {
    var entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class);
    var entitySQLMetaDataManager = new EntitySQLMetaDataImpl<>(entityClassMetaDataManager);
    var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor, entityClassMetaDataManager, entitySQLMetaDataManager);

    var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
    dbServiceManager.saveManager(new Manager("ManagerFirst"));

    var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));
    var managerSecondSelected = dbServiceManager
      .getManager(managerSecond.getNo())
      .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()));
    log.info("managerSecondSelected:{}", managerSecondSelected);
    managerSecondSelected.setLabel("UpdatedLabel");
    managerSecondSelected.setParam1("UpdatedSomeParam1");
    log.info("updated managerSecondSelected:{}", managerSecondSelected);

    var allManagers = dbServiceManager.findAll();
    log.info("Managers:{}", allManagers);
  }

}
