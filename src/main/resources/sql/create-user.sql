create user 'ilona'@'%' identified by 'secret';
create database Ilona;
grant all privileges on Ilona.* to 'ilona'@'%';

create database IlonaTest;
grant all privileges on IlonaTest.* to 'ilona'@'%';
