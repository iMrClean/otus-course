package ru.otus.course.api;

public interface SensorDataProcessingFlow {

    void startProcessing();

    void stopProcessing();

    void bindProcessor(String roomPattern, SensorDataProcessor processor);

}
