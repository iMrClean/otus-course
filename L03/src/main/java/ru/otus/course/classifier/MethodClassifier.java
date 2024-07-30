package ru.otus.course.classifier;

import ru.otus.course.enums.AnnotationType;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Stepnin Konstantin. Created on 31.07.2024
 */
public interface MethodClassifier {

  void classify(Method method, Map<AnnotationType, List<Method>> methodMap);

}
