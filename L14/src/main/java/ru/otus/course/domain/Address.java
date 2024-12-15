package ru.otus.course.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("address")
public class Address {

  @Id
  private Long id;

  private String street;

  private Long clientId;

  public Address() {
  }

  public Address(Long clientId) {
    this.clientId = clientId;
  }

  public Address(String street, Long clientId) {
    this.street = street;
    this.clientId = clientId;
  }

}
