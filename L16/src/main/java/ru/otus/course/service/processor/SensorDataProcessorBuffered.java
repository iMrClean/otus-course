package ru.otus.course.service.processor;

import lombok.extern.slf4j.Slf4j;
import ru.otus.course.api.SensorDataProcessor;
import ru.otus.course.api.model.SensorData;
import ru.otus.course.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
@SuppressWarnings({"java:S1068", "java:S125"})
public class SensorDataProcessorBuffered implements SensorDataProcessor {

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final PriorityBlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new PriorityBlockingQueue<>(bufferSize, Comparator.comparing(SensorData::measurementTime));
    }

    @Override
    public void process(SensorData data) {
        dataBuffer.put(data);
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        try {
            List<SensorData> dataBufferList = new ArrayList<>();
            dataBuffer.drainTo(dataBufferList);

            if (!dataBufferList.isEmpty()) {
                writer.writeBufferedData(dataBufferList);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }

}
