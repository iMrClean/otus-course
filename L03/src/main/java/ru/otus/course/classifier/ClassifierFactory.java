package ru.otus.course.classifier;

import ru.otus.course.classifier.impl.*;
import ru.otus.course.enums.AnnotationType;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Stepnin Konstantin. Created on 31.07.2024
 */
public class ClassifierFactory {

  private static final List<MethodClassifier> DEFAULT_CLASSIFIERS = List.of(
    new BeforeAllClassifier(),
    new BeforeClassifier(),
    new TestClassifier(),
    new AfterClassifier(),
    new AfterAllClassifier()
  );

  private static final List<MethodClassifier> CORE_CLASSIFIERS = List.of(
    new BeforeClassifier(),
    new TestClassifier(),
    new AfterClassifier()
  );

  private ClassifierFactory() {
  }

  /**
   * Создает и возвращает список классификаторов методов.
   *
   * @param useOnlyCoreClassifiers Использует ли кастомные классификаторы.
   * @return Список классификаторов.
   */
  public static List<MethodClassifier> createClassifiers(boolean useOnlyCoreClassifiers) {
    return useOnlyCoreClassifiers ? CORE_CLASSIFIERS : DEFAULT_CLASSIFIERS;
  }

  /**
   * Классифицирует методы, распределяя их по спискам в зависимости от аннотаций.
   *
   * @param methods                Массив методов, которые нужно классифицировать.
   * @param methodMap              Карта, в которую будут добавлены классифицированные методы.
   * @param useOnlyCoreClassifiers Использует ли кастомные классификаторы.
   */
  public static void classifyMethods(Method[] methods, Map<AnnotationType, List<Method>> methodMap, boolean useOnlyCoreClassifiers) {
    List<MethodClassifier> classifiers = createClassifiers(useOnlyCoreClassifiers);

    Arrays.stream(methods).forEach(method -> classifyMethod(method, classifiers, methodMap));
  }

  /**
   * Классифицирует один метод с помощью списка классификаторов.
   *
   * @param method      Метод, который нужно классифицировать.
   * @param classifiers Список классификаторов.
   * @param methodMap   Карта, в которую будут добавлены классифицированные методы.
   */
  private static void classifyMethod(Method method, List<MethodClassifier> classifiers, Map<AnnotationType, List<Method>> methodMap) {
    classifiers.forEach(classifier -> classifier.classify(method, methodMap));
  }

}
