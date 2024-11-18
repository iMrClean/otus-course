create table phone
(
  id        bigserial,
  number    varchar(50),
  client_id bigint not null
);
