package com.revature.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.revature.daos.ConnectionUtil;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;

public class UserController {
	
	private static Scanner scan = new Scanner(System.in);

	public void updateUserInfo() throws SQLException {
		Connection connect = ConnectionUtil.getConnection();
		Statement statement = connect.createStatement();
		ResultSet result = null;
		String userName = "";
		String passWord = "";
		
		while(true) {
			System.out.println("Please re-login to your account: \n"
					+ "Username:");
			userName = scan.nextLine();
			System.out.println("Password:");
			passWord = scan.nextLine();
			//must try again if user name is already in use
			result = statement.executeQuery("SELECT * FROM bank_account WHERE user_name = '" + userName + "' AND pass_word = '" + passWord + "';" );
			if(result.next()) {
				System.out.println("Thank you, verification complete.");
				break;
			}else {
			System.out.println("Sorry that was incorrect. Please try again. \n"
					+ "Username:");
			userName = scan.nextLine();
			System.out.println("Password:");
			passWord = scan.nextLine();
			}
			
		}
	
//		System.out.println("What is your first name?");
//		String firstName = scan.nextLine();
//		
//		System.out.println("What is your last name?");
//		String lastName = scan.nextLine();
		
		System.out.println("What is your address?");
		String address = scan.nextLine();
		
		
		System.out.println("What is your phone number?");
		String phoneNumber = scan.nextLine();
		
		System.out.println("What is your email?");
		String email = scan.nextLine();
		
		//User user = new User(userName, passWord, firstName ,lastName, phoneNumber, address, email, 1, true);
		
		statement.executeUpdate("UPDATE user_info SET address = '" + address +"', phone_number = '" + phoneNumber + "', email = '" + email + "' WHERE username = '" + userName + "';");
		
		System.out.println("\n**** Update complete **** \n");
	}
	
	public void viewUser() throws SQLException {
		Connection connect = ConnectionUtil.getConnection();
		Statement statement = connect.createStatement();
		ResultSet result = null;
		
		UserDAOImpl userDAO = new UserDAOImpl();
		String name = "";
		while(true) {
			System.out.println("What is the username of the profile you'd like to view?");
			name = scan.nextLine();
		//needs to not allow existing user names 
			//must try again if user name is already in use
			result = statement.executeQuery("SELECT * FROM user_info WHERE username = '" + name + "'");
			if(result.next()) {
				System.out.println("Thank you.");
				break;
			}else {
			System.out.println("Sorry that was username doesn't match anything in our system. Please try again.");
			name = scan.nextLine();
			}
			
		}
		User view = userDAO.findByUsersName(name);
		System.out.println(view);
		
	}
	
}
