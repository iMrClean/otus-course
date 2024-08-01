package ru.otus.course.classifier.impl;

import ru.otus.course.annotation.Before;
import ru.otus.course.classifier.MethodClassifier;
import ru.otus.course.enums.AnnotationType;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Stepnin Konstantin. Created on 31.07.2024
 */
public class BeforeClassifier implements MethodClassifier {

  @Override
  public void classify(Method method, Map<AnnotationType, List<Method>> methodMap) {
    if (method.isAnnotationPresent(Before.class)) {
      methodMap.get(AnnotationType.BEFORE).add(method);
    }
  }

}
