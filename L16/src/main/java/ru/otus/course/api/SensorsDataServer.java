package ru.otus.course.api;

import ru.otus.course.api.model.SensorData;

public interface SensorsDataServer {

  void onReceive(SensorData sensorData);

}
