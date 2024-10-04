package ru.otus.course.jdbc.mapper;

import ru.otus.course.crm.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

  private final Class<T> clazz;

  public EntityClassMetaDataImpl(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public String getName() {
    return clazz.getSimpleName();
  }

  @Override
  public Constructor<T> getConstructor() {
    return Stream.of(clazz.getDeclaredConstructors())
      .filter(constructor -> constructor.getParameterCount() == 0)
      .map(constructor -> (Constructor<T>) constructor)
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Class " + clazz.getName() + " has no default constructor"));
  }

  @Override
  public Field getIdField() {
    return Stream.of(clazz.getDeclaredFields())
      .filter(field -> field.isAnnotationPresent(Id.class))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Id not found in class " + clazz.getName()));
  }

  @Override
  public List<Field> getAllFields() {
    return Stream.of(clazz.getDeclaredFields())
      .toList();
  }

  @Override
  public List<Field> getFieldsWithoutId() {
    return Stream.of(clazz.getDeclaredFields())
      .filter(field -> !field.isAnnotationPresent(Id.class))
      .toList();
  }

}
