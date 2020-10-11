create user if not exists 'ilona'@'%' identified by 'secret';
create database if not exists Ilona;
grant all privileges on Ilona.* to 'ilona'@'%';
create database if not exists IlonaTest;
grant all privileges on IlonaTest.* to 'ilona'@'%';
