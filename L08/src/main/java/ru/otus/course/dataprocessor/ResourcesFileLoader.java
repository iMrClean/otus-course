package ru.otus.course.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.course.model.Measurement;

import java.io.IOException;
import java.util.List;

public class ResourcesFileLoader implements Loader {

  private final String fileName;

  private final ObjectMapper mapper;

  public ResourcesFileLoader(String fileName) {
    this.fileName = fileName;
    this.mapper = new ObjectMapper();
  }

  @Override
  public List<Measurement> load() throws IOException {
    try (var inputStream = getClass().getResourceAsStream("/" + fileName)) {
      return List.of(mapper.readValue(inputStream, Measurement[].class));
    }
  }

}
