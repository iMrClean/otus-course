package ru.otus.course.service.impl;

import ru.otus.course.service.TestLogging;
import ru.otus.course.annotation.Log;

public class TestLoggingImpl implements TestLogging {

  @Log
  @Override
  public void calculation(int param1) {

  }

  @Log
  @Override
  public void calculation(int param1, int param2) {

  }

  @Override
  public void calculation(int param1, int param2, String param3) {

  }

}
