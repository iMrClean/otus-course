package ru.otus.course.lib;

import ru.otus.course.api.model.SensorData;

import java.util.List;

public interface SensorDataBufferedWriter {

  void writeBufferedData(List<SensorData> bufferedData);

}
