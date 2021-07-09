package com.revature.main;

import java.sql.SQLException;
import com.revature.controllers.*;
import com.revature.models.Account;

public class Driver {

	
	public static void main(String[] args) throws SQLException {
		System.out.println("\n ***** Welcome to Bank of Revature ***** \n");

		
		AccountController login = new AccountController();
		Account account = new Account();
		login.StartApp();
		login.enterAccount(account);
		
		
		
		
	}

}
