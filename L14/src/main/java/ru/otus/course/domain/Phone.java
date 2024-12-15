package ru.otus.course.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Data
@Table("phone")
public class Phone {

  @Id
  private Long id;

  @NonNull
  private String number;

  private Long clientId;

  public Phone() {
  }

  public Phone(Long clientId) {
    this.clientId = clientId;
  }

  public Phone(Long phoneId, String phoneNumber, Long clientId) {
    this.id = phoneId;
    this.number = phoneNumber;
    this.clientId = clientId;
  }

}
