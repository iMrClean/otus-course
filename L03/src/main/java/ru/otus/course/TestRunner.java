package ru.otus.course;

import ru.otus.course.classifier.ClassifierFactory;
import ru.otus.course.enums.AnnotationType;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Stepnin Konstantin. Created on 31.07.2024
 */
public class TestRunner {

  private static final Logger logger = Logger.getLogger(TestRunner.class.getName());

  public static void main(String[] args) {
    runTests(MyTests.class.getName());
  }

  /**
   * Запускает тесты для указанного класса.
   *
   * @param className Имя класса с тестами.
   */
  public static void runTests(String className) {
    try {
      Class<?> testClass = Class.forName(className);

      Map<AnnotationType, List<Method>> methodMap = AnnotationType.getMethodMap();

      ClassifierFactory.classifyMethods(testClass.getDeclaredMethods(), methodMap, false);

      TestExecutor.executeTests(testClass, methodMap);

    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "Test class not found: " + className, e);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "An unexpected error occurred while running tests", e);
    }
  }

}
