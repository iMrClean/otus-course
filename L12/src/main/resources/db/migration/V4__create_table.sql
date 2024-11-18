create table sec_user
(
  id       bigserial,
  login    varchar(50) unique,
  password varchar(50)
)
