package ru.otus.course.jdbc.mapper;

public interface EntitySQLMetaData {

  String getSelectAllSql();

  String getSelectByIdSql();

  String getInsertSql();

  String getUpdateSql();

}
