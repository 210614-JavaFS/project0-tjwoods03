package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;

public class AccountDAOImpl implements AccountDAO{

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
				account.setName(result.getString("full_name"));
				account.setUser(result.getString("user_name"));
				account.setAccountBalance(result.getDouble("account_balance"));
				list.add(account);
			}
		
			return list;
		
		}catch(SQLException e) {
				e.printStackTrace();
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
				account.setName(result.getString("full_name"));
				account.setUser(result.getString("user_name"));
				account.setPass(result.getString("pass_word"));
				account.setAccountBalance(result.getDouble("account_balance"));
			}
			
			return account;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAccount(Account account) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO bank_account (full_name, user_name, pass_word, account_level, account_balance)"
					+ " VALUES (?,?,?,?,?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setString(++index, account.getName());
			statement.setString(++index, account.getUser());
			statement.setString(++index, account.getPass());
			statement.setInt(++index, account.getLevel());
			statement.setDouble(++index, account.getAccountBalance());
			
			statement.execute();
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
}
