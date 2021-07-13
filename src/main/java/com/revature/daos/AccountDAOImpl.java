package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Account;

public class AccountDAOImpl implements AccountDAO{
	
	private static Logger log = LoggerFactory.getLogger(Account.class);
	
	@Override
	public List<Account> findAllAccounts() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM bank_account";
		
			Statement statement = conn.createStatement();
		
			ResultSet result = statement.executeQuery(sql);
		
			List<Account> list = new ArrayList<>();
		
		//Result sets have a cursor similarly to Scanner or other I/O classes.
			while(result.next()) {
				Account account = new Account();
				account.setAccountID(result.getInt("account_id"));
				account.setFirstName(result.getString("first_name"));
				account.setLastName(result.getString("first_name"));
				account.setUser(result.getString("user_name"));
				account.setCheckingsName(result.getString("checkings_name"));
				account.setCheckingsBalance(result.getDouble("checkings_balance"));
				account.setSavingsName(result.getString("account_type"));
				list.add(account);
			}
		
			return list;
		
		}catch(SQLException e) {
				e.printStackTrace();
				log.warn("No accounts were to be found.");
		}
	return null;
	}

	@Override
	public Account findByAccountName(String name) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM bank_account WHERE user_name = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			//This is where SQL injection is checked for.
			statement.setString(1, name);
			
			ResultSet result = statement.executeQuery();
			
			Account account = new Account();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				account.setAccountID(result.getInt("account_id"));
				account.setFirstName(result.getString("first_name"));
				account.setLastName(result.getString("last_name"));
				account.setUser(result.getString("user_name"));
				account.setCheckingsName(result.getString("checkings_name"));
				account.setCheckingsBalance(result.getDouble("checkings_balance"));
				account.setSavingsName(result.getString("savings_name"));
				account.setSavingsBalance(result.getDouble("savings_balance"));
			}
			
			return account;
			
		}catch(SQLException e) {
			e.printStackTrace();
			log.warn("This account couldn't be found.");
		}
		return null;
	}
	
	@Override
	public boolean addAccount(Account account) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO bank_account (first_name, last_name, user_name, pass_word, checkings_name, checkings_balance, savings_name, savings_balance)"
					+ " VALUES (?,?,?,?,?,?,?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, account.getFirstName());
			statement.setString(++index, account.getLastName());
			statement.setString(++index, account.getUser());
			statement.setString(++index, account.getPass());
			statement.setString(++index, account.getCheckingsName());
			statement.setDouble(++index, account.getCheckingsBalance());
			statement.setString(++index, account.getSavingsName());
			statement.setDouble(++index, account.getSavingsBalance());
			
			statement.execute();
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
			log.warn("The account wasn't able to be added.");
		}
		return false;
	}

}
