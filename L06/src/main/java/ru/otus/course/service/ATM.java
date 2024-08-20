package ru.otus.course.service;

import ru.otus.course.domain.Banknote;
import ru.otus.course.domain.Currency;

import java.util.List;
import java.util.Map;

public interface ATM {

  /**
   * Получить баланс в банкомате, вычисленный как сумма всех банкнот.
   *
   * @return Общий баланс в банкомате.
   */
  Map<Currency, Double> getBalance();

  /**
   * Внести банкноты в банкомат.
   */
  void deposit(List<Banknote> banknotes);


  /**
   * Выдать запрашиваемую сумму минимальным количеством банкнот.
   */
  List<Banknote> withdraw(double amount, Currency currency);

}
