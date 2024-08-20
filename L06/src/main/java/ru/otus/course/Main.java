package ru.otus.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.course.domain.Banknote;
import ru.otus.course.domain.Currency;
import ru.otus.course.service.ATM;
import ru.otus.course.service.BankATM;
import ru.otus.course.storage.BankStorage;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  private static final ATM atm = new BankATM(new BankStorage());

  public static void main(String[] args) {
    logBalance();
    atm.deposit(Collections.singletonList(new Banknote(1, Currency.USD)));
    atm.deposit(Collections.singletonList(new Banknote(2, Currency.USD)));
    atm.deposit(Collections.singletonList(new Banknote(5, Currency.USD)));
    atm.deposit(Collections.singletonList(new Banknote(10, Currency.USD)));
    atm.deposit(Collections.singletonList(new Banknote(20, Currency.USD)));
    atm.deposit(Collections.singletonList(new Banknote(50, Currency.USD)));
    atm.deposit(Collections.singletonList(new Banknote(100, Currency.USD)));

    atm.deposit(Collections.singletonList(new Banknote(5, Currency.EUR)));
    atm.deposit(Collections.singletonList(new Banknote(10, Currency.EUR)));
    atm.deposit(Collections.singletonList(new Banknote(20, Currency.EUR)));
    atm.deposit(Collections.singletonList(new Banknote(50, Currency.EUR)));
    atm.deposit(Collections.singletonList(new Banknote(100, Currency.EUR)));
    atm.deposit(Collections.singletonList(new Banknote(500, Currency.EUR)));

    atm.deposit(Collections.singletonList(new Banknote(100, Currency.RUB)));
    atm.deposit(Collections.singletonList(new Banknote(200, Currency.RUB)));
    atm.deposit(Collections.singletonList(new Banknote(500, Currency.RUB)));
    atm.deposit(Collections.singletonList(new Banknote(1000, Currency.RUB)));
    atm.deposit(Collections.singletonList(new Banknote(2000, Currency.RUB)));
    atm.deposit(Collections.singletonList(new Banknote(5000, Currency.RUB)));

    logBalance();

    List<Banknote> withdraw = atm.withdraw(1000, Currency.RUB);
    log.info("Выдано {}", withdraw);
    logBalance();
  }

  private static void logBalance() {
    Map<Currency, Double> balance = atm.getBalance();
    log.info("Баланс [RUB {}]", balance.get(Currency.RUB));
    log.info("Баланс {}", "EUR " + balance.get(Currency.EUR));
    log.info("Баланс {}", "USD " + balance.get(Currency.USD));
  }

}
