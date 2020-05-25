create user 'ilona'@'%' identified by 'secret';
create database Ilona;
grant all privileges on Ilona.* to 'ilona'@'%'
