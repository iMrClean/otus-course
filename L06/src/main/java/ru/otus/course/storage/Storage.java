package ru.otus.course.storage;

import ru.otus.course.domain.Banknote;
import ru.otus.course.domain.Currency;

import java.util.List;
import java.util.Map;

public interface Storage {

  /**
   * Добавление банкнот из списка
   */
  void deposit(List<Banknote> banknotes);

  /**
   * Получение общей суммы
   *
   * @return общая сумма
   */
  Map<Currency, Double> getBalance();

  Map<Currency, Map<Banknote, Integer>> getStorage();
}
