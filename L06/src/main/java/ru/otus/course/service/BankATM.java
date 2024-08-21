package ru.otus.course.service;

import ru.otus.course.domain.Banknote;
import ru.otus.course.domain.Currency;
import ru.otus.course.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankATM implements ATM {

  private final Storage storage;

  public BankATM(Storage storage) {
    this.storage = storage;
  }

  @Override
  public Map<Currency, Double> getBalance() {
    return storage.getBalance();
  }

  @Override
  public void deposit(List<Banknote> banknotes) {
    if (banknotes.isEmpty()) {
      throw new IllegalArgumentException("Список банкнот пуст");
    }

    if (banknotes.stream().anyMatch(banknote -> !banknote.currency().equals(banknotes.getFirst().currency()))) {
      throw new IllegalArgumentException("Список содержит банкноты разных валют");
    }

    storage.deposit(banknotes);
  }

  @Override
  public List<Banknote> withdraw(double amount, Currency currency) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Сумма для снятия должна быть положительной");
    }

    Map<Currency, Map<Banknote, Integer>> stor = storage.getStorage();
    Map<Banknote, Integer> currencyStorage = stor.get(currency);
    if (currencyStorage == null) {
      throw new IllegalArgumentException("В банкомате нет валюты: " + currency);
    }

    List<Banknote> withdrawnBanknotes = new ArrayList<>();
    double remainingAmount = amount;

    List<Banknote> availableBanknotes = new ArrayList<>(currencyStorage.keySet());
    availableBanknotes.sort((b1, b2) -> Double.compare(b2.denomination(), b1.denomination()));

    for (Banknote banknote : availableBanknotes) {
      int count = currencyStorage.get(banknote);
      while (remainingAmount >= banknote.denomination() && count > 0) {
        withdrawnBanknotes.add(banknote);
        remainingAmount -= banknote.denomination();
        count--;
        currencyStorage.put(banknote, count); // обновляем количество купюр в хранилище
      }
      if (remainingAmount == 0) {
        break; // если вся сумма снята, выходим из цикла
      }
    }

    if (remainingAmount > 0) {
      // не удалось снять всю сумму
      throw new IllegalArgumentException("Недостаточно средств в банкомате");
    }

    return withdrawnBanknotes;
  }

}
