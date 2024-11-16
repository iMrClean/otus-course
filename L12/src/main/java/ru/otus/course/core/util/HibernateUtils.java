package ru.otus.course.core.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;

@UtilityClass
public final class HibernateUtils {

  public SessionFactory buildSessionFactory(Configuration configuration, Class<?>... annotatedClasses) {
    var metadataSources = new MetadataSources(createServiceRegistry(configuration));
    Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);

    var metadata = metadataSources.getMetadataBuilder().build();
    return metadata.getSessionFactoryBuilder().build();
  }

  private StandardServiceRegistry createServiceRegistry(Configuration configuration) {
    return new StandardServiceRegistryBuilder()
      .applySettings(configuration.getProperties())
      .build();
  }

}
