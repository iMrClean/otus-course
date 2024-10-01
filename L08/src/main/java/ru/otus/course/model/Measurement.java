package ru.otus.course.model;

public record Measurement(String name, double value) {

  @Override
  public String toString() {
    return "Measurement{" +
      "name='" + name + '\'' +
      ", value=" + value +
      '}';
  }

}
