create database my_db;

use my_db;

create table users(
user_id int primary key auto_increment,
name varchar(200),
email_id varchar(20) unique,
number varchar(10)) ;

select * from users;

create table flights(
flight_id int primary key auto_increment,
airline varchar(200),
departure varchar(50),
destination varchar(50),
date DATE,
no_of_seats int ,
price decimal(10,2));

insert into flights(airline,departure,destination,date,no_of_seats,price)
 values("qatar","bengaluru","goa","2025-04-24","65",'9344.54');

 insert into flights(airline,departure,destination,date,no_of_seats,price)
values('indigo','bengaluru','goa','2025-04-14','0','2500');

insert into flights(airline,departure,destination,date,no_of_seats,price)
values('indigo','mumbai','chennai','2025-04-10','55','2500.92'),
('indianExpress','delhi','mumbai','2025-04-19','80','25000'),
('vistara','kolkata','goa','2025-05-29','90','5000'),
 ("qatar","bengaluru","goa","2025-05-24","68",'9344.54');
 
 insert into flights(airline,departure,destination,date,no_of_seats,price)
 values('vistara','mumbai','chennai','2025-05-30','55','2800.92'),
('indianExpress','delhi','bengaluru','2025-04-12','80','25000'),
('vistara','kolkata','hyderabad','2025-05-21','90','5000'),
('indianExpress','mumbai','bengaluru','2025-04-12','80','25000'),
('vistara','chennai','hyderabad','2025-05-21','90','5000'),
 ("indianExpress","bengaluru","hyderabad","2025-05-08","80",'9344.54');

select * from flights;

create table booking(
booking_id int primary key auto_increment,
user_id int,
flight_id int,
seat_number int,
status enum ('conformed', 'canceled') default 'conformed');
select * from booking;