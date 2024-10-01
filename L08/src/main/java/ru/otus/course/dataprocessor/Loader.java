package ru.otus.course.dataprocessor;

import ru.otus.course.model.Measurement;

import java.io.IOException;
import java.util.List;

public interface Loader {

  List<Measurement> load() throws IOException;

}
