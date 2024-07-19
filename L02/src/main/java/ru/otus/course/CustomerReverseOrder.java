package ru.otus.course;

import java.util.LinkedList;

/**
 * @author Stepnin Konstantin. Created on 18.07.2024
 */
public class CustomerReverseOrder {

  private final LinkedList<Customer> customers = new LinkedList<>();

  public void add(Customer customer) {
    customers.addFirst(customer);
  }

  public Customer take() {
    return customers.isEmpty() ? null : customers.removeFirst();
  }

}
