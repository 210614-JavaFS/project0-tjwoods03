package com.revature.accounts;

import java.util.*;

public abstract class CreateAccount {

	String user;
	String pass;
	char type;
	double balance;

	//If statement for if user name is new and greater than 4 characters
	//If statement for if password is long enough
	
	//Account creation
	CreateAccount(String user, String pass, char type){
		
		this.user = user;
		this.pass = pass;
		this.type = type;
		this.balance = 0;
		
	}
	

}
