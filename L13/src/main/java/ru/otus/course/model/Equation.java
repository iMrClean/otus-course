package ru.otus.course.model;

import lombok.Getter;

@Getter
public abstract class Equation {

  protected final int leftPart;

  protected final int rightPart;

  protected final int result;

  protected Equation(int leftPart, int rightPart) {
    this.leftPart = leftPart;
    this.rightPart = rightPart;
    this.result = calcResult();
  }

  protected abstract int calcResult();

}
