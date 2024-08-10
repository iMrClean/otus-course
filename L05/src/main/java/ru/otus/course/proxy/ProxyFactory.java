package ru.otus.course.proxy;

import ru.otus.course.handler.LoggingInvocationHandler;

import java.lang.reflect.Proxy;

public class ProxyFactory {

  @SuppressWarnings("unchecked")
  public static <T> T createProxy(T target, Class<T> interfaceType) {
    return (T) Proxy.newProxyInstance(
      target.getClass().getClassLoader(),
      new Class<?>[] { interfaceType },
      new LoggingInvocationHandler(target));
  }

}
