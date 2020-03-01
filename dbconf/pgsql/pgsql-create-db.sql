create database diagram_test;

\c diagram_test;

create table person (id int primary key, name varchar(100), age int);
create table pet  (id int primary key, name varchar(100), age int, owner int, foreign key (owner) references person(id));