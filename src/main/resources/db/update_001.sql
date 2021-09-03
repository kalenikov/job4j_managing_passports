create table passports (
id serial primary key,
series varchar (20),
number integer,
end_date DATE
);
insert into passports (series, number, end_date) values ('4505', 123456, '2021-08-28');
insert into passports (series, number, end_date) values ('4505', 123457, '2021-08-27');
insert into passports (series, number, end_date) values ('4505', 123458, '2021-09-25');
insert into passports (series, number, end_date) values ('4505', 123459, '2022-09-28');