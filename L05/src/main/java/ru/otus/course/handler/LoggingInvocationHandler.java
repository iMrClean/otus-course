package ru.otus.course.handler;

import ru.otus.course.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingInvocationHandler implements InvocationHandler {

  private static final Logger log = LoggerFactory.getLogger(LoggingInvocationHandler.class);

  private final Object target;

  public LoggingInvocationHandler(Object target) {
    this.target = target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (method.isAnnotationPresent(Log.class)) {
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
