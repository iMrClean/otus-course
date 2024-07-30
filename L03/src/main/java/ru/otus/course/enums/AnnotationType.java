package ru.otus.course.enums;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stepnin Konstantin. Created on 31.07.2024
 */
public enum AnnotationType {

  BEFORE_ALL,
  BEFORE,
  TEST,
  AFTER,
  AFTER_ALL;

  private static final Map<AnnotationType, List<Method>> methodMap = initializeMethodMap();

  private static Map<AnnotationType, List<Method>> initializeMethodMap() {
    return Arrays.stream(AnnotationType.values())
      .collect(Collectors.toMap(type -> type, type -> new ArrayList<>(), (a, b) -> a, LinkedHashMap::new));
  }

  public static Map<AnnotationType, List<Method>> getMethodMap() {
    return Collections.unmodifiableMap(methodMap);
  }

}
