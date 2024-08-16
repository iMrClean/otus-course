package ru.otus.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Написать эмулятор АТМ (банкомата).
 * АТМ должен уметь:
 * Принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
 * Выдавать запрошенную сумму минимальным количеством банкнот или ошибку, если сумму нельзя выдать.
 * Выдавать сумму остатка денежных средств
 */
public class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  private static final ATM atm = new ATM();

  public static void main(String[] args) {
    initializeATM();
    logBalance("Баланс после инициализации");

    int amount = 2700;
    processWithdrawal(amount);

    logBalance("Баланс после первого снятия");

    atm.deposit(10, 10);
    logBalance("Баланс после внесения новых банкнот");

    processWithdrawal(amount);

    logBalance("Баланс после второго снятия");
  }

  /**
   * Инициализировать банкомат начальными значениями банкнот.
   */
  private static void initializeATM() {
    atm.deposit(100, 10);
    atm.deposit(50, 20);
    atm.deposit(20, 30);
  }

  /**
   * Выполнить обработку запроса на снятие наличных.
   *
   * @param amount Запрашиваемая сумма.
   */
  private static void processWithdrawal(int amount) {
    try {
      log.info("Запрашиваемый баланс: {}", amount);
      Map<Integer, Integer> withdrawnNotes = atm.withdraw(amount);
      log.info("Выданные банкноты: {}", withdrawnNotes);
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * Текущий баланс банкомата.
   *
   * @param message Сообщение для логирования.
   */
  private static void logBalance(String message) {
    log.info("{}: {}", message, atm.getBalance());
  }

}
