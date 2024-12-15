package ru.otus.course.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.course.domain.Client;

import java.util.List;

public interface ClientRepository extends ListCrudRepository<Client, Long> {

  @Query(value = """
    select c.id as client_id,
           c.name as client_name,
           p.id as phone_id,
           p.number as phone_number,
           a.street as address_street
    from client c
           left join phone p on c.id = p.client_id
           left join address a on c.id = a.client_id
    order by c.id
    """, resultSetExtractorClass = ClientResultSetExtractor.class)
  List<Client> findAllByCustomQuery();

}
