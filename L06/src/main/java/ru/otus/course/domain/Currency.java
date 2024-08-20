package ru.otus.course.domain;

public enum Currency {

  RUB,
  USD,
  EUR;

  public boolean isInvalidDenomination(int denomination) {
    return switch (this) {
      case RUB -> denomination < 100 || denomination > 5000;
      case USD -> denomination < 1 || denomination > 100;
      case EUR -> denomination < 5 || denomination > 500;
    };
  }

}
