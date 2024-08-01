package ru.otus.course;

import ru.otus.course.annotation.*;

/**
 * @author Stepnin Konstantin. Created on 31.07.2024
 */
public class MyTests {

  @BeforeAll
  public static void beforeAll() {
    System.out.println("===================BEFORE ALL===================");
  }

  @Before
  public void setUp() {
    System.out.println("Setting up before test");
  }

  @Test
  public void test1() {
    System.out.println("Executing test 1");
  }

  @Test
  public void test2() {
    System.out.println("Executing test 2");
    throw new RuntimeException("ERROR");
  }

  @Test
  public void test3() {
    System.out.println("Executing test 3");
  }

  @After
  public void tearDown() {
    System.out.println("Tearing down after test");
  }

  @AfterAll
  public static void afterAll() {
    System.out.println("===================AFTER  ALL===================");
  }

}
