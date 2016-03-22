insert into person (first_name, last_name) values ('Leung', 'Wong');
insert into person (first_name, last_name) values ('Leon', 'Hwang');

//insert into visit (check_in, person_id) values ('2016-03-02 13:40:00',1);
insert into visit (check_in, person_id) values ('2016-03-02 13:37:00',2);

insert into visit (check_in, check_out, person_id) values ('2016-03-02 13:40:00', '2016-03-02 13:45:00',1);
insert into visit (check_in, check_out, person_id) values ('2016-03-03 13:40:00', '2016-03-03 13:45:00',1);
insert into visit (check_in, check_out, person_id) values ('2016-03-04 13:40:00', '2016-03-04 13:45:00',1);