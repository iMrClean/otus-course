package ru.otus.course.crm.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 50)
  private String name;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  @ToString.Exclude
  @Fetch(FetchMode.JOIN)
  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Phone> phones = new ArrayList<>();

  public Client(String name) {
    this.id = null;
    this.name = name;
  }

  public Client(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Client(Long id, String name, Address address, List<Phone> phones) {
    this.id = id;
    this.name = name;
    this.address = Optional.ofNullable(address).orElse(new Address());
    this.phones = List.copyOf(phones);
    this.phones.forEach(phone -> phone.setClient(this));
  }

  @Override
  public Client clone() {
    return new Client(this.id, this.name, this.address != null ? this.address.clone() : null, this.phones.stream().map(Phone::clone).toList());
  }

  @Override
  public final boolean equals(Object object) {
    if (this == object) return true;
    if (object == null) return false;
    Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    Client client = (Client) object;
    return getId() != null && Objects.equals(getId(), client.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }

}
