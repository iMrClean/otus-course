package ru.otus.course.core.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;

public final class HibernateUtils {

  private HibernateUtils() {
  }

  public static SessionFactory buildSessionFactory(Configuration configuration, Class<?>... annotatedClasses) {
    var metadataSources = new MetadataSources(createServiceRegistry(configuration));
    Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);

    var metadata = metadataSources.getMetadataBuilder().build();
    return metadata.getSessionFactoryBuilder().build();
  }

  private static StandardServiceRegistry createServiceRegistry(Configuration configuration) {
    return new StandardServiceRegistryBuilder()
      .applySettings(configuration.getProperties())
      .build();
  }

}
