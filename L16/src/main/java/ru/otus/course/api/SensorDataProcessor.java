package ru.otus.course.api;

import ru.otus.course.api.model.SensorData;

public interface SensorDataProcessor {

    void process(SensorData data);

    default void onProcessingEnd() {

    }

}
