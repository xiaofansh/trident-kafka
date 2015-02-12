//192.168.75.102
create database test;
use test;
create table message(tid char(100) not null primary key) engine=Innodb;
create table transaction(xid int not null primary key auto_increment, buyer_id int not null,amount int not null) engine=Innodb;

insert into message(tid) values("xx");

create procedure insertTransaction
(v_tid char(100),
 v_buyerid int,
 v_amount int
)
begin
declare t_id char(100);
select tid into t_id from message limit 1;
if t_id != v_tid then
start transaction;
update message set tid = v_tid where tid = t_id;
insert into transaction(buyer_id,amount) values(v_buyerid,v_amount);  
commit;
end if;  
end;

//192.168.75.104
create database test;
use test;
create table message(tid char(100) not null primary key) engine=Innodb;

create table user(userid int not null primary key, amt_bought int not null) engine=Innodb;

insert into message(tid) values("xx");

create procedure UpdateUserAmount
(v_tid char(100),v_userid int,v_amount int)
begin
declare t_id char(100);
declare t_amount int default 0;
declare t_sum int default 0;
select tid into t_id from message limit 1;
if t_id != v_tid then
start transaction;
select amt_bought into t_amount from user where userid = v_userid limit 1;
if t_amount = 0 then
update message set tid = v_tid where tid = t_id;
insert into user(userid,amt_bought) values(v_userid,v_amount);  
else
set t_sum = t_amount + v_amount;
update message set tid = v_tid where tid = t_id;
update user set amt_bought = t_sum where userid = v_userid;
end if; 
commit;
end if;  
end;


