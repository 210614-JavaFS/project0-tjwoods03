DROP TABLE IF EXISTS bank_account;

DROP TABLE IF EXISTS user_info;

CREATE TABLE bank_account(
account_id SERIAL PRIMARY KEY,
full_name varchar(50),
user_name varchar(50) UNIQUE NOT null, pass_word varchar(50) NOT null, 
account_level Integer NOT null,
account_type varchar(50), account_balance numeric(20,2),
join_date Timestamp DEFAULT Current_Timestamp
);

INSERT INTO BANK_ACCOUNT (user_name, pass_word, account_level)
	VALUES ('employee', 'employee', 2),
	('admin', 'admin', 3);
	
CREATE TABLE user_info(
user_name varchar(50) REFERENCES bank_account(user_name),
full_name varchar(50),
address varchar(100),
phone_number varchar(15),
email varchar(75)
);

--SELECT account_balance FROM bank_account WHERE user_name = 'asdf';

--UPDATE BANK_ACCOUNT SET ACCOUNT_BALANCE = 1234 WHERE user_name = 'asdf';
