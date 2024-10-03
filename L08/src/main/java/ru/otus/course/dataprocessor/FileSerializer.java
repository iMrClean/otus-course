package ru.otus.course.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

  private final String fileName;

  private final ObjectMapper mapper;

  public FileSerializer(String fileName) {
    this.fileName = fileName;
    this.mapper = new ObjectMapper();
  }

  @Override
  public void serialize(Map<String, Double> data) throws IOException {
    mapper.writeValue(new File(fileName), data);
  }

}
