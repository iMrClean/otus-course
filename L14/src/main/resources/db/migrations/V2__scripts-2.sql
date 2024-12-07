create table address
(
  id        bigserial primary key,
  street    varchar(50) not null,
  client_id bigint      not null,
  foreign key (client_id) references client (id) on delete cascade
);

