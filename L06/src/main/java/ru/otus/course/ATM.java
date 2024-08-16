package ru.otus.course;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий банкомат, который управляет банкнотами разных номиналов.
 */
public class ATM {

  private final Map<Integer, Banknote> banknotes = new HashMap<>();

  /**
   * Получить баланс в банкомате, вычисленный как сумма всех банкнот.
   *
   * @return Общий баланс в банкомате.
   */
  public double getBalance() {
    return banknotes.values()
      .stream()
      .mapToDouble(banknote -> banknote.getDenomination() * banknote.getQuantity())
      .sum();
  }

  /**
   * Выдать запрашиваемую сумму минимальным количеством банкнот.
   *
   * @param amount Запрашиваемая сумма.
   * @return Карта, содержащая номиналы банкнот и их количество, которые были выданы.
   * @throws IllegalArgumentException Если сумму нельзя выдать.
   */
  public Map<Integer, Integer> withdraw(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Сумма должна быть положительной.");
    }

    Map<Integer, Integer> tempResult = new HashMap<>();
    Map<Integer, Integer> backupQuantities = new HashMap<>();
    int remainingAmount = amount;

    try {
      for (Map.Entry<Integer, Banknote> entry : banknotes.entrySet()) {
        int denomination = entry.getKey();
        Banknote banknote = entry.getValue();

        if (denomination > remainingAmount)
          continue;

        int count = Math.min(remainingAmount / denomination, banknote.getQuantity());
        if (count > 0) {
          backupQuantities.put(denomination, banknote.getQuantity());

          tempResult.put(denomination, count);
          remainingAmount -= count * denomination;
        }
      }

      if (remainingAmount > 0) {
        rollbackWithdraw(backupQuantities);
        throw new IllegalArgumentException("Невозможно выдать запрашиваемую сумму.");
      }

      applyWithdraw(tempResult);

      return tempResult;

    } catch (Exception e) {
      rollbackWithdraw(backupQuantities);
      throw e;
    }
  }

  /**
   * Применить изменения после успешного выполнения выдачи.
   *
   * @param result Результат выдачи.
   */
  private void applyWithdraw(Map<Integer, Integer> result) {
    for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
      int denomination = entry.getKey();
      int count = entry.getValue();
      Banknote banknote = banknotes.get(denomination);
      banknote.decreaseQuantity(count);
    }
  }

  /**
   * Откатить изменения, если выдача не удалась.
   *
   * @param backupQuantities Резервные данные о количестве банкнот.
   */
  private void rollbackWithdraw(Map<Integer, Integer> backupQuantities) {
    for (Map.Entry<Integer, Integer> entry : backupQuantities.entrySet()) {
      int denomination = entry.getKey();
      int originalQuantity = entry.getValue();
      Banknote banknote = banknotes.get(denomination);
      banknote.setQuantity(originalQuantity);
    }
  }

  /**
   * Внести банкноты в банкомат.
   *
   * @param denomination Номинал банкнот.
   * @param quantity     Количество банкнот.
   */
  public void deposit(int denomination, int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Количество банкнот должно быть положительным.");
    }

    Banknote banknote = banknotes.get(denomination);
    if (banknote == null) {
      banknotes.put(denomination, new Banknote(denomination, quantity));
    } else {
      banknote.increaseQuantity(quantity);
    }
  }

}
