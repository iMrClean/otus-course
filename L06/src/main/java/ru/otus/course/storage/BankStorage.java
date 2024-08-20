package ru.otus.course.storage;

import ru.otus.course.domain.Banknote;
import ru.otus.course.domain.Currency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankStorage implements Storage {

  private final Map<Currency, Map<Banknote, Integer>> storage = new HashMap<>();

  @Override
  public void deposit(List<Banknote> banknotes) {
    for (Banknote banknote : banknotes) {
      Currency currency = banknote.currency();
      if (!storage.containsKey(currency)) {
        storage.put(currency, new HashMap<>());
      }
      Map<Banknote, Integer> currencyStorage = storage.get(currency);
      if (currencyStorage.containsKey(banknote)) {
        currencyStorage.put(banknote, currencyStorage.get(banknote) + 1);
      } else {
        currencyStorage.put(banknote, 1);
      }
    }
  }

  @Override
  public Map<Currency, Double> getBalance() {
    return storage.entrySet().stream()
      .collect(HashMap::new,
        (map, entry) -> map.put(entry.getKey(), entry.getValue().entrySet().stream()
          .mapToDouble(banknoteEntry -> banknoteEntry.getKey().denomination() * banknoteEntry.getValue())
          .sum()),
        HashMap::putAll);
  }

  @Override
  public Map<Currency, Map<Banknote, Integer>> getStorage() {
    return storage;
  }

}
