use bankdb;
INSERT INTO Product (name,price,stockQuantity) VALUES
	 ('Laptop',25000.0,10),
	 ('Samrtphone',5000.0,12),
     ('Watch',1500.0,50),
     ('TV',75000.0,4);
DELETE FROM `bankdb`.`orders` WHERE (`id` = '102');     
select * from product;
select * from orders; 
SELECT * FROM auditlog; 
set session transaction isolation level read uncommitted;
set session transaction isolation level read committed;
set session transaction isolation level REPEATABLE READ;
set session transaction isolation level serializable;
show variables like 'transaction_isolation';   
show table status where name = 'product';
set global transaction_isolation ='serializable';
set session transaction_isolation = 'RUN_UNCOMMITTED';
alter table product ENGINE = MyISAM;