package ru.otus.course.service;

import lombok.RequiredArgsConstructor;
import ru.otus.course.api.SensorsDataChannel;
import ru.otus.course.api.SensorsDataServer;
import ru.otus.course.api.model.SensorData;

@RequiredArgsConstructor
public class SensorsDataServerImpl implements SensorsDataServer {

    private final SensorsDataChannel sensorsDataChannel;

    @Override
    public void onReceive(SensorData sensorData) {
        sensorsDataChannel.push(sensorData);
    }

}
