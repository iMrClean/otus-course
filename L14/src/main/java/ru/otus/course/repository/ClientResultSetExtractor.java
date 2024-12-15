package ru.otus.course.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.NonNull;
import ru.otus.course.domain.Address;
import ru.otus.course.domain.Client;
import ru.otus.course.domain.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientResultSetExtractor implements ResultSetExtractor<List<Client>> {

  @Override
  public List<Client> extractData(@NonNull ResultSet rs) throws DataAccessException, SQLException {
    var clients = new ArrayList<Client>();

    while (rs.next()) {
      var clientId = rs.getLong("client_id");
      var clientName = rs.getString("client_name");
      var phoneId = rs.getLong("phone_id");
      var phoneNumber = rs.getString("phone_number");
      var addressStreet = rs.getString("address_street");


      var client = clients.stream()
        .filter(c -> c.getId().equals(clientId))
        .findFirst()
        .orElseGet(() -> {
          var address = new Address(addressStreet, clientId);
          var newClient = new Client(clientId, clientName, address);
          clients.add(newClient);
          return newClient;
        });

      client.getPhones().add(new Phone(phoneId, phoneNumber, clientId));
    }

    return clients;
  }

}
