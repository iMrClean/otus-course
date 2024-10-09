package ru.otus.course.jdbc.mapper;

import org.springframework.util.ReflectionUtils;
import ru.otus.course.core.repository.DataTemplate;
import ru.otus.course.core.repository.DataTemplateException;
import ru.otus.course.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataTemplateJdbc<T> implements DataTemplate<T> {

  private final DbExecutor dbExecutor;

  private final EntityClassMetaData<T> entityClassMetaDataClient;

  private final EntitySQLMetaData entitySQLMetaData;

  public DataTemplateJdbc(DbExecutor dbExecutor, EntityClassMetaDataImpl<T> entityClassMetaDataClient, EntitySQLMetaData entitySQLMetaData) {
    this.dbExecutor = dbExecutor;
    this.entityClassMetaDataClient = entityClassMetaDataClient;
    this.entitySQLMetaData = entitySQLMetaData;
  }

  @Override
  public Optional<T> findById(Connection connection, Long id) {
    return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
      try {
        return rs.next() ? map(rs) : null;
      } catch (SQLException e) {
        throw new DataTemplateException(e);
      }
    });
  }

  @Override
  public List<T> findAll(Connection connection) {
    return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(), rs -> {
      List<T> result = new ArrayList<>();
      try {
        while (rs.next()) {
          result.add(map(rs));
        }
      } catch (SQLException e) {
        throw new DataTemplateException(e);
      }
      return result;
    }).orElse(List.of());
  }

  @Override
  public Long insert(Connection connection, T object) {
    return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), entityClassMetaDataClient.getFieldsWithoutId()
      .stream()
      .map(field -> {
        field.setAccessible(true);
        return ReflectionUtils.getField(field, object);
      })
      .toList());
  }

  @Override
  public void update(Connection connection, T object) {
    var params = entityClassMetaDataClient.getAllFields()
      .stream()
      .map(field -> {
        field.setAccessible(true);
        return ReflectionUtils.getField(field, object);
      })
      .toList();

    var reversed = params.reversed();

    dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), reversed);
  }

  private T map(ResultSet rs) {
    try {
      var constructor = entityClassMetaDataClient.getConstructor();
      var object = constructor.newInstance();

      var allFields = entityClassMetaDataClient.getAllFields();
      for (Field field : allFields) {
        field.setAccessible(true);
        field.set(object, rs.getObject(field.getName()));
      }

      return object;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
