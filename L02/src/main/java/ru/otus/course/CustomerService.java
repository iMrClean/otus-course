package ru.otus.course;

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

  private Map.Entry<Customer, String> getEntry(Map.Entry<Customer, String> firstEntry) {
    return Map.entry(
      new Customer(firstEntry.getKey().getId(), firstEntry.getKey().getName(), firstEntry.getKey().getScores()), firstEntry.getValue()
    );
  }

  public void add(Customer customer, String data) {
    customers.put(customer, data);
  }

  public Map.Entry<Customer, String> getSmallest() {
    Map.Entry<Customer, String> firstEntry = customers.firstEntry();
    if (firstEntry == null) {
      return null;
    }
    return getEntry(firstEntry);
  }

  public Map.Entry<Customer, String> getNext(Customer customer) {
    Map.Entry<Customer, String> nextEntry = customers.higherEntry(customer);
    if (nextEntry == null || nextEntry.getKey().getScores() <= customer.getScores()) {
      return null;
    }

    return getEntry(nextEntry);
  }

}
