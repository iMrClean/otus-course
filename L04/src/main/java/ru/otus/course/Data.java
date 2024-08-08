package ru.otus.course;

public class Data {
  //before
//  private final Integer value;
  private Integer value;
  //after
  private int _value;

  public Data() {
  }

  public Data(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }

  public int get_value() {
    return _value;
  }

  public void set_value(int _value) {
    this._value = _value;
  }

}
