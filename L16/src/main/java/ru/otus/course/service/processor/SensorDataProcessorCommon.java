package ru.otus.course.service.processor;

import lombok.extern.slf4j.Slf4j;
import ru.otus.course.api.SensorDataProcessor;
import ru.otus.course.api.model.SensorData;

@Slf4j
public class SensorDataProcessorCommon implements SensorDataProcessor {

    @Override
    public void process(SensorData data) {
        if (data.value() == null || data.value().isNaN()) {
            return;
        }
        log.info("Обработка данных: {}", data);
    }

}
