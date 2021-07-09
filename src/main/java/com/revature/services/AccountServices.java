package com.revature.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.controllers.AccountController;
import com.revature.daos.AccountDAOImpl;
import com.revature.daos.AccountDAO;
import com.revature.models.Account;

public class AccountServices {
	
	private static AccountDAO accountDAO = new AccountDAOImpl();
	
	public List<Account> getAllAccounts(){
		return accountDAO.findAllAccounts();
	}
	
	public Account getAccount(String name) {
		return accountDAO.findByAccountName(name);
	}
	
	public boolean addAccount(Account account) {
		return accountDAO.addAccount(account);
	}
	
}
