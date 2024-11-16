package ru.otus.course.crm.migration;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import ru.otus.course.core.config.HibernateConfiguration;

@Slf4j
public class MigrationsExecutorFlyway {

  private final Flyway flyway;

  public static MigrationsExecutorFlyway createMigrationsExecutorFlyway(HibernateConfiguration configuration) {
    return new MigrationsExecutorFlyway(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
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
