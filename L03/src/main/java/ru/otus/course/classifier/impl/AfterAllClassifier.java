package ru.otus.course.classifier.impl;

import ru.otus.course.annotation.AfterAll;
import ru.otus.course.classifier.MethodClassifier;
import ru.otus.course.enums.AnnotationType;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Stepnin Konstantin. Created on 31.07.2024
 */
public class AfterAllClassifier implements MethodClassifier {

  @Override
  public void classify(Method method, Map<AnnotationType, List<Method>> methodMap) {
    if (method.isAnnotationPresent(AfterAll.class)) {
      if (!java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
        throw new IllegalStateException("Method " + method.getName() + " annotated with @AfterAll must be static");
      }
      methodMap.get(AnnotationType.AFTER_ALL).add(method);
    }
  }

}
