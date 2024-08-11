package ru.otus.course.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.course.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggingInvocationHandler implements InvocationHandler {

  private static final Logger log = LoggerFactory.getLogger(LoggingInvocationHandler.class);

  private final Object target;
  private final Set<Method> cache;

  public LoggingInvocationHandler(Object target) {
    this.target = target;
    this.cache = Arrays.stream(target.getClass().getMethods())
      .filter(method -> method.isAnnotationPresent(Log.class))
      .collect(Collectors.toSet());
  }

  private boolean isMethodLoggable(Method method) {
    return cache.stream()
      .anyMatch(m -> m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes()));
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (isMethodLoggable(method)) {
      StringBuilder logMessage = new StringBuilder();
      logMessage.append("executed method: ").append(method.getName());

      if (args != null && args.length > 0) {
        for (int i = 0; i < args.length; i++) {
          logMessage.append(", param").append(i + 1).append(": ").append(args[i]);
        }
      }

      log.info(logMessage.toString());
    }

    return method.invoke(target, args);
  }

}
