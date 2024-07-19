package ru.otus.course;

import java.util.Objects;

/**
 * @author Stepnin Konstantin. Created on 18.07.2024
 */
public class Customer {

  private final long id;
  private String name;
  private long scores;

  public Customer(long id, String name, long scores) {
    this.id = id;
    this.name = name;
    this.scores = scores;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getScores() {
    return scores;
  }

  public void setScores(long scores) {
    this.scores = scores;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Customer customer = (Customer) o;
    return id == customer.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
