package ru.otus.course;

import ru.otus.course.enums.AnnotationType;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для выполнения методов тестов, включая методы BeforeAll, тесты и AfterAll.
 */
public class TestExecutor {

  private static final Logger logger = Logger.getLogger(TestExecutor.class.getName());

  /**
   * Выполняет тесты для указанного класса.
   *
   * @param testClass Класс с тестами.
   * @param methodMap Карта методов, классифицированных по аннотациям.
   * @throws Exception Если происходит ошибка при выполнении методов.
   */
  public static void executeTests(Class<?> testClass, Map<AnnotationType, List<Method>> methodMap) throws Exception {
    boolean beforeAllSucceeded = false;
    try {
      // Выполнение методов BeforeAll
      invokeMethods(methodMap.get(AnnotationType.BEFORE_ALL), null);
      beforeAllSucceeded = true;
    } finally {
      if (!beforeAllSucceeded) {
        // Если BeforeAll завершился с ошибкой, все равно выполнить AfterAll
        invokeMethods(methodMap.get(AnnotationType.AFTER_ALL), null);
      }
    }

    // Выполнение тестов
    executeTestMethods(methodMap, testClass);

    // Выполнение методов AfterAll
    invokeMethods(methodMap.get(AnnotationType.AFTER_ALL), null);
  }

  /**
   * Вызывает методы в списке.
   *
   * @param methods  Список методов для вызова.
   * @param instance Экземпляр объекта, на котором вызываются методы (может быть null).
   * @throws Exception Если происходит ошибка при вызове метода.
   */
  private static void invokeMethods(List<Method> methods, Object instance) throws Exception {
    for (Method method : methods) {
      invokeMethod(method, instance);
    }
  }

  /**
   * Вызывает один метод на данном экземпляре объекта.
   *
   * @param method   Метод для вызова.
   * @param instance Экземпляр объекта, на котором вызывается метод (может быть null).
   * @throws Exception Если происходит ошибка при вызове метода.
   */
  private static void invokeMethod(Method method, Object instance) throws Exception {
    if (Modifier.isStatic(method.getModifiers())) {
      logger.info("Invoking static method: " + method.getName());
      method.invoke(null);
    } else {
      logger.info("Invoking method: " + method.getName());
      method.invoke(instance);
    }
  }

  /**
   * Выполняет тестовые методы, обрабатывая перед- и после-методы.
   *
   * @param methodMap Карта методов, классифицированных по аннотациям.
   * @param testClass Класс теста.
   */
  private static void executeTestMethods(Map<AnnotationType, List<Method>> methodMap, Class<?> testClass) throws Exception {
    List<Method> beforeMethods = methodMap.get(AnnotationType.BEFORE);
    List<Method> testMethods = methodMap.get(AnnotationType.TEST);
    List<Method> afterMethods = methodMap.get(AnnotationType.AFTER);

    int totalTests = testMethods.size();
    int passedTests = 0;

    for (Method testMethod : testMethods) {
      Object testInstance = null;
      try {
        testInstance = testClass.getDeclaredConstructor().newInstance();
        invokeMethods(beforeMethods, testInstance);
        invokeMethod(testMethod, testInstance);
        passedTests++;
      } catch (Exception e) {
        logger.log(Level.SEVERE, "Test " + testMethod.getName() + " failed: ", e.getCause());
      } finally {
        invokeMethods(afterMethods, testInstance);
      }
    }

    logTestResults(totalTests, passedTests, totalTests - passedTests);
  }

  /**
   * Логирует результаты выполнения тестов.
   *
   * @param totalTests  Общее количество тестов.
   * @param passedTests Количество прошедших тестов.
   * @param failedTests Количество упавших тестов.
   */
  private static void logTestResults(int totalTests, int passedTests, int failedTests) {
    logger.info(String.format("Total tests: %d", totalTests));
    logger.info(String.format("Passed tests: %d", passedTests));
    logger.info(String.format("Failed tests: %d", failedTests));
  }

}
