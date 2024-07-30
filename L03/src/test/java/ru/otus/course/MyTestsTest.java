package ru.otus.course;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author Stepnin Konstantin. Created on 31.07.2024
 */
class MyTestsTest {

  @Test
  public void testMyTests() {
    assertDoesNotThrow(() -> TestRunner.runTests("ru.otus.course.MyTests"));
  }

}
