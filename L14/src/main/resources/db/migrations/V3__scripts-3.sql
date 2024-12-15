create table phone
(
  id        bigserial primary key,
  number    varchar(20) not null,
  client_id bigint      not null,
  foreign key (client_id) references client (id) on delete cascade
);
