package ru.otus.course;

import java.util.ArrayDeque;

/**
 * @author Stepnin Konstantin. Created on 18.07.2024
 */
public class CustomerReverseOrder {

  private final ArrayDeque<Customer> customers = new ArrayDeque<>();

  public void add(Customer customer) {
    customers.addFirst(customer);
  }

  public Customer take() {
    return customers.isEmpty() ? null : customers.pollFirst();
  }

}
