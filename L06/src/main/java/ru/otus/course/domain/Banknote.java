package ru.otus.course.domain;

public record Banknote(Integer denomination, Currency currency) {

  public Banknote {
    if (currency.isInvalidDenomination(denomination)) {
      throw new IllegalArgumentException("Недопустимый номинал: " + denomination);
    }
  }

  @Override
  public String toString() {
    return currency + " " + denomination;
  }

}
