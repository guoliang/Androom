DROP TABLE person IF EXISTS;
DROP TABLE visit IF EXISTS;

CREATE TABLE person (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

create table visit (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL, 
  check_in datetime,
  check_out datetime,
  person_id bigint,
  primary key (id)
);