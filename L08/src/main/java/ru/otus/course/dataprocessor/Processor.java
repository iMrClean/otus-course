package ru.otus.course.dataprocessor;

import ru.otus.course.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

  Map<String, Double> process(List<Measurement> data);

}
