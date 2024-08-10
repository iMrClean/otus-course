package ru.otus.course;

import ru.otus.course.proxy.ProxyFactory;
import ru.otus.course.service.TestLogging;
import ru.otus.course.service.impl.TestLoggingImpl;

public class Main {

  public static void main(String[] args) {

    TestLogging original = new TestLoggingImpl();
    TestLogging proxy = ProxyFactory.createProxy(original, TestLogging.class);

    proxy.calculation(1);
    proxy.calculation(1, 2);
    proxy.calculation(1, 2, "3");
  }

}
