package ru.otus.course;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Stepnin Konstantin. Created on 18.07.2024
 */
public class CustomerService {

  private final TreeMap<Customer, String> customers = new TreeMap<>(
    Comparator.comparingLong(Customer::getScores)
  );

  private static AbstractMap.SimpleImmutableEntry<Customer, String> apply(Map.Entry<Customer, String> entry) {
    long id = entry.getKey().getId();
    String name = entry.getKey().getName();
    long scores = entry.getKey().getScores();
    String data = entry.getValue();

    return new AbstractMap.SimpleImmutableEntry<>(new Customer(id, name, scores), data);
  }

  public void add(Customer customer, String data) {
    customers.put(customer, data);
  }

  public Map.Entry<Customer, String> getSmallest() {
    return customers.entrySet()
      .stream()
      .findFirst()
      .map(CustomerService::apply)
      .orElse(null);
  }

  public Map.Entry<Customer, String> getNext(Customer customer) {
    return customers.entrySet()
      .stream()
      .filter(entry -> entry.getKey().getScores() > customer.getScores())
      .findFirst()
      .map(CustomerService::apply)
      .orElse(null);
  }

}
