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
import com.revature.models.User;

public class UserDAOImpl implements UserDAO{
	
	private static Logger log = LoggerFactory.getLogger(Account.class);

	@Override
	public List<User> findAllUsers() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM user_info";
		
			Statement statement = conn.createStatement();
		
			ResultSet result = statement.executeQuery(sql);
		
			List<User> list = new ArrayList<>();
		
		//Result sets have a cursor similarly to Scanner or other I/O classes.
			while(result.next()) {
				User user = new User();
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setUserName(result.getString("user_name"));
				user.setAddress(result.getString("address"));
				user.setPhoneNumber(result.getString("phone_number"));
				user.setEmail(result.getString("email"));
				list.add(user);
			}
		
			return list;
		
		}catch(SQLException e) {
				e.printStackTrace();
				log.warn("No users were to be found.");
		}
	return null;
	}

	@Override
	public User findByUsersName(String name) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM user_info WHERE username = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			//This is where SQL injection is checked for.
			statement.setString(1, name);
			
			ResultSet result = statement.executeQuery();
			
			User user = new User();
			
			//ResultSets have a cursor similarly to Scanners or other I/O classes. 
			while(result.next()) {
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setUserName(result.getString("username"));
				user.setAddress(result.getString("address"));
				user.setPhoneNumber(result.getString("phone_number"));
				user.setEmail(result.getString("email"));
				user.setAccountLevel(result.getInt("account_level"));
				user.setActive(result.getBoolean("active"));
			}
			
			return user;
			
		}catch(SQLException e) {
			e.printStackTrace();
			log.warn("The user wasn't able to be found");
		}
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO user_info (first_name, last_name, address, phone_number, email, account_level, active)"
					+ " VALUES (?,?,?,?,?,?,?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);


			int index = 0;
			statement.setString(++index, user.getFirstName());
			statement.setString(++index, user.getLastName());
			statement.setString(++index, user.getAddress());
			statement.setString(++index, user.getPhoneNumber());
			statement.setString(++index, user.getEmail());
			statement.setInt(++index, user.getAccountLevel());
			statement.setBoolean(++index, user.isActive());
			
			statement.execute();
		
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
			log.warn("The user wasn't able to be updated.");
		}
		
		return false;
	}

	@Override
	public boolean addUser(User user) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO user_info (username, pass_word, first_name, last_name, address, phone_number, email, account_level, active)"
					+ " VALUES (?,?,?,?,?,?,?,?,?);";
			
			String sql2 = "INSERT INTO bank_account(first_name, last_name, user_name, pass_word, checkings_name, savings_name)"
					+ " VALUES (?,?,?,?,?,?)";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			PreparedStatement statementB = conn.prepareStatement(sql2);

			int index = 0;
			statement.setString(++index, user.getUserName());
			statement.setString(++index, user.getPass());
			statement.setString(++index, user.getFirstName());
			statement.setString(++index, user.getLastName());
			statement.setString(++index, user.getAddress());
			statement.setString(++index, user.getPhoneNumber());
			statement.setString(++index, user.getEmail());
			statement.setInt(++index, user.getAccountLevel());
			statement.setBoolean(++index,  user.isActive());
			
			statement.execute();
			
			int j = 0;
			statementB.setString(++j, user.getFirstName());
			statementB.setString(++j, user.getLastName());
			statementB.setString(++j, user.getUserName());
			statementB.setString(++j, user.getPass());
			statementB.setString(++j, user.getFirstName() + " checkings");
			statementB.setString(++j, user.getFirstName() + " savings");
			
			statementB.execute();
			
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
			log.warn("The user wasn't able to be added.");
		}
		return false;
	}

}
