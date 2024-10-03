package ru.otus.course.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjectForMessage {

  private List<String> data;

  public List<String> getData() {
    return data;
  }

  public void setData(List<String> data) {
    this.data = data;
  }

  @Override
  public ObjectForMessage clone() {
    ObjectForMessage cloned = new ObjectForMessage();

    cloned.setData(Optional.ofNullable(this.data)
      .map(ArrayList::new)
      .orElse(null));

    return cloned;
  }

  @Override
  public String toString() {
    return "ObjectForMessage{" +
      "data=" + data +
      '}';
  }

}
