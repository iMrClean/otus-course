package ru.otus.course.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

  private final EntityClassMetaData<T> entityClassMetaData;

  public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
    this.entityClassMetaData = entityClassMetaData;
  }

  private String buildSql(String template, Object... args) {
    return String.format(template, args);
  }

  private String buildColumnList(List<Field> fields) {
    return fields.stream()
      .map(Field::getName)
      .collect(Collectors.joining(", "));
  }

  private String buildInsertValues(List<Field> fields) {
    return fields.stream()
      .map(f -> "?")
      .collect(Collectors.joining(","));
  }

  private String buildSetClause(List<Field> fields) {
    return fields.stream()
      .map(f -> f.getName() + " = ?")
      .collect(Collectors.joining(", "));
  }

  @Override
  public String getSelectAllSql() {
    return buildSql("SELECT * FROM %s", entityClassMetaData.getName());
  }

  @Override
  public String getSelectByIdSql() {
    return buildSql("SELECT * FROM %s WHERE %s = ?", entityClassMetaData.getName(), entityClassMetaData.getIdField().getName());
  }

  @Override
  public String getInsertSql() {
    var fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
    var columns = buildColumnList(fieldsWithoutId);
    var values = buildInsertValues(fieldsWithoutId);

    return buildSql("INSERT INTO %s(%s) VALUES (%s)", entityClassMetaData.getName(), columns, values);
  }

  @Override
  public String getUpdateSql() {
    var values = buildSetClause(entityClassMetaData.getFieldsWithoutId());

    return buildSql("UPDATE %s SET %s WHERE %s = ?", entityClassMetaData.getName(), values, entityClassMetaData.getIdField().getName());
  }

}
