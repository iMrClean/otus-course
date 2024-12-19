package ru.otus.course.api;

import ru.otus.course.api.model.SensorData;

import java.util.concurrent.TimeUnit;

public interface SensorsDataChannel {

  boolean push(SensorData sensorData);

  boolean isEmpty();

  SensorData take(long timeout, TimeUnit unit) throws InterruptedException;

}
