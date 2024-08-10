package ru.otus.course;

import java.util.ArrayList;
import java.util.List;

public class Summator {
  //before
  private Integer sum = 0;
  private Integer prevValue = 0;
  private Integer prevPrevValue = 0;
  private Integer sumLastThreeValues = 0;
  private Integer someValue = 0;
  private final List<Data> listValues = new ArrayList<>();
  //after
  private int _sum = 0;
  private int _prevValue = 0;
  private int _prevPrevValue = 0;
  private int _sumLastThreeValues = 0;
  private int _someValue = 0;
  private final List<Data> _listValues = new ArrayList<>();

  public void calc(Data data, boolean withOptimization) {
    if (withOptimization) {
      processingAfterOptimization(data);
    } else {
      processingDefault(data);
    }
  }

  private void processingDefault(Data data) {
    listValues.add(data);
    if (listValues.size() % 100_000 == 0) {
      listValues.clear();
    }
    sum += data.getValue();

    sumLastThreeValues = data.getValue() + prevValue + prevPrevValue;

    prevPrevValue = prevValue;
    prevValue = data.getValue();

    for (var idx = 0; idx < 3; idx++) {
      someValue += (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum);
      someValue = Math.abs(someValue) + listValues.size();
    }
  }

  private void processingAfterOptimization(Data data) {
    _listValues.add(data);
    if (_listValues.size() % 100_000 == 0) {
      _listValues.clear();
    }
    _sum += data.get_value();

    _sumLastThreeValues = data.get_value() + _prevValue + _prevPrevValue;

    _prevPrevValue = _prevValue;
    _prevValue = data.get_value();

    for (var idx = 0; idx < 3; idx++) {
      _someValue += (_sumLastThreeValues * _sumLastThreeValues / (data.get_value() + 1) - _sum);
      _someValue = Math.abs(_someValue) + _listValues.size();
    }
  }

  public Integer getSum() {
    return sum;
  }

  public Integer getPrevValue() {
    return prevValue;
  }

  public Integer getPrevPrevValue() {
    return prevPrevValue;
  }

  public Integer getSumLastThreeValues() {
    return sumLastThreeValues;
  }

  public Integer getSomeValue() {
    return someValue;
  }

  public int get_sum() {
    return _sum;
  }

  public int get_prevValue() {
    return _prevValue;
  }

  public int get_prevPrevValue() {
    return _prevPrevValue;
  }

  public int get_sumLastThreeValues() {
    return _sumLastThreeValues;
  }

  public int get_someValue() {
    return _someValue;
  }

}
