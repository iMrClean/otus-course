package ru.otus.course.crm.migration;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

@Slf4j
public class MigrationsExecutorFlyway {

  private final Flyway flyway;

  public static MigrationsExecutorFlyway createMigrationsExecutorFlyway(String url, String username, String password) {
    return new MigrationsExecutorFlyway(url, username, password);
  }

  private MigrationsExecutorFlyway(String url, String username, String password) {
    flyway = Flyway.configure()
      .dataSource(url, username, password)
      .locations("classpath:/db/migration")
      .load();
  }

  public void executeMigrations() {
    log.info("db migration started...");
    flyway.migrate();
    log.info("db migration finished.");
  }

}
