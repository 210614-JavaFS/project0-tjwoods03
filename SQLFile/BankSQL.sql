DROP TABLE IF EXISTS bank_account;

DROP TABLE IF EXISTS user_info;

CREATE TABLE bank_account(
account_id SERIAL PRIMARY KEY,
first_name varchar(50),
last_name varchar(50),
user_name varchar(50) REFERENCES user_info(username), pass_word varchar(50) NOT null, 
checkings_name varchar(75)NULL NULL, checkings_balance numeric(20,2),
savings_name varchar(75)NULL NULL, savings_balance numeric(20,2),
join_date Timestamp DEFAULT Current_Timestamp
);

INSERT INTO BANK_ACCOUNT (user_name, pass_word )
	VALUES ('employee', 'employee'),
	('admin', 'admin');
	
CREATE TABLE user_info(
username varchar(50) UNIQUE NOT NULL,
pass_word varchar(50) NOT NULL,
first_name varchar(50),
last_name varchar(50),
address varchar(100),
phone_number varchar(15),
email varchar(75),
account_level Integer NOT null,
active boolean
);

INSERT INTO user_info (username, pass_word, account_level, active)
	VALUES ('employee', 'employee', '2', true),
	('admin', 'admin', '3', true);