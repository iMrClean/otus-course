package ru.otus.course.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Data
@Table("client")
public class Client {

  @Id
  private Long id;

  private String name;

  @MappedCollection(idColumn = "client_id")
  private Address address;

  @MappedCollection(idColumn = "client_id")
  private Set<Phone> phones;

  public Client() {
    this.address = new Address(this.id);
    this.phones = Set.of(new Phone(this.id));
  }

  public Client(Long id, String name, Address address) {
    this.id = id;
    this.name = name;
    this.phones = new HashSet<>();
    this.address = address;
  }

}
