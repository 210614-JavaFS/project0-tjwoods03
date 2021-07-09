package com.revature.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.revature.services.AccountServices;
import com.revature.daos.ConnectionUtil;
import com.revature.models.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


//main way of using application
public class AccountController{

	private static AccountServices accountService = new AccountServices();
	private static Logger log = LoggerFactory.getLogger(AccountController.class);
	private static AccountController account = new AccountController();
	private ArrayList<Account> addAccounts = new ArrayList<Account>();
	//private static Connection connect = AccountDAO.establishConnection();
	
	
	private Scanner scan = new Scanner(System.in);
	
	protected String name;
	protected String user;
	protected String pass;
	protected int level;
	protected double balance;
	
	public Account StartApp() throws SQLException {
		
		Account login = new Account();
		
		System.out.println("Are you: \n"
				+ "1) A new user. \n"
				+ "2) An existing user. \n"
				+ "3) Leave bank.");
		String response = scan.nextLine();
		switch(response) {
			case "1":
				newAccount();
				break;
			case "2":
				account.enterAccount(login);
				return null;
			case "3":
				System.out.println("Thanks for visiting Bank of Revature. We hope to see you again soon!");
				break;
			default:
				System.out.println("Invalid input, please try again.");	
				login = StartApp();
				break;
		}
		return login;
	}
	
	
	
	
	
	public void enterAccount(Account account) throws SQLException {
			
			Connection connect = ConnectionUtil.getConnection();
			Statement statement = connect.createStatement();
			ResultSet result = null;
		
			System.out.println("\n *****Please login***** \n");
			System.out.println("Enter username:"); 
			String userName = scan.nextLine();
			System.out.println("Enter password:");
			String password = scan.nextLine();
	
			int accountLevel = AccountController.checkValidAccount(userName,password);

			if(accountLevel > 0) {
				//Customer login
				System.out.println("\n *****Login Successful***** \n");
				if(accountLevel == 1) {
				System.out.println("Welcome, how can we help you?");
				System.out.println("You can: \n"
						+ "1) View balances. \n"
						+ "2) Create a new account. (Chacking or Savings) \n"
						+ "3) Transfer. \n"
						+ "4) Make a withdrawl. \n"
						+ "5) Make a deposit. \n"
						+ "6) Update personal information. \n"
						+ "7) Exit.");
				String choice = scan.nextLine();
				switch(choice) {
					//View balances
					case "1":
						try(Connection conn = ConnectionUtil.getConnection()){
						ResultSet getBalance = statement.executeQuery("SELECT account_balance FROM bank_account WHERE user_name = '" + userName + "';");
						while(getBalance.next()) {
						Double balance = getBalance.getDouble(1);
						System.out.println("You currently have $" +balance + " in your account.");
						}
						}catch(SQLException e) {
							e.printStackTrace();
						}
						break;
					//Create a new checking or savings account	
					case "2":
						
						break;
					//Transfer money between accounts. Can only do so if have more than one account
					case "3":
						//list current accounts with balances
						System.out.println("What account would you like to transfer from: ");
						
						//list current accounts with balances
						System.out.println("What account would you like to transfer to: ");
						
						break;
					//Withdraw money from account
					case "4":
						//list current accounts with balances
//						System.out.println("What account would you like to withdraw: ");
//						
//						System.out.println("How much would you like to withdraw? ");
						
						try(Connection conn = ConnectionUtil.getConnection()){
							ResultSet getBalance = statement.executeQuery("SELECT account_balance FROM bank_account WHERE user_name = '" + userName + "';");
							while(getBalance.next()) {
							Double balance = getBalance.getDouble(1);
							System.out.println("How much would you like to withdraw? You currently have " + balance + " in your account.");
							double amount = scan.nextDouble();
							while(amount > balance) {
								System.out.println("You can't take out more than you currently have. Please try again.");
								amount = scan.nextDouble();
							}
							statement.executeQuery("UPDATE bank_account SET account_balance = " + (balance - amount) + " WHERE user_name = '" + userName +"';");
							ResultSet getCurrentBalance = statement.executeQuery("SELECT account_balance FROM bank_account WHERE user_name = '" + userName + "';");
								while(getCurrentBalance.next()) {
									Double currentBalance = getCurrentBalance.getDouble(1);
									System.out.println("You currently have $" + currentBalance + " in your account.");
								}
							}
							}catch(SQLException e) {
								e.printStackTrace();
							}
						
						break;
					//Deposit money into account
					case "5":
						//list current accounts with balances
//						System.out.println("What account would you like to deposit to: ");
//						
//						System.out.println("How much would you like to deposit? ");
						
						try(Connection conn = ConnectionUtil.getConnection()){
							ResultSet getBalance = statement.executeQuery("SELECT account_balance FROM bank_account WHERE user_name = '" + userName + "';");
							while(getBalance.next()) {
							Double balance = getBalance.getDouble(1);
							System.out.println("How much would you like to deposit? You currently have " + balance + " in your account.");
							double amount = scan.nextDouble();
							while(amount < 0) {
								System.out.println("You can't enter a negative number. Please try again.");
								amount = scan.nextDouble();
							}
							statement.executeQuery("UPDATE bank_account SET account_balance = " + (balance + amount) + " WHERE user_name = '" + userName +"';");
							ResultSet getCurrentBalance = statement.executeQuery("SELECT account_balance FROM bank_account WHERE user_name = '" + userName + "';");
								while(getCurrentBalance.next()) {
									Double currentBalance = getCurrentBalance.getDouble(1);
									System.out.println("You currently have $" + currentBalance + " in your account.");
								}
							}
							}catch(SQLException e) {
								e.printStackTrace();
							}
					//Update or add personal information
					case "6":
		
						break;
					//Leave program
					case "7":
						System.out.println("\n  *****Thank you for visiting the Bank of Revature!***** \n");
						System.exit(0);
					default:
						System.out.println("Invalid input, please try again.");
						
						return;
					}
				} //end of customer if statement
				//Employee login
				else if(accountLevel == 2) {
					
				} 
				//Admin login
				else if(accountLevel == 3){
					
				}else {
					System.out.println("Attempt to login unsuccessful. Please try again.");
					enterAccount(account);
				}
				
			}//end of if statement enterAccount
		
	}//end of enterAccount
	
	public void newAccount() throws SQLException{
		Connection connect = ConnectionUtil.getConnection();
		Statement statement = connect.createStatement();
		ResultSet result = null;
		String user = "";
		System.out.println("What is your name?");
		String name = scan.nextLine();
		while(true) {
			System.out.println("What do you want your username to be? (Must be at least 4 characters)");
			user = scan.nextLine();
		//needs to not allow existing user names 
		
			if(user.length() < 4) {
				while(user.length() < 4) {
				System.out.println("Invalid entry. Username not long enough, please try again.");
				user = scan.nextLine();
				}
			} 
			//must try again if user name is already in use
			result = statement.executeQuery("SELECT * FROM bank_account WHERE user_name = '" + user + "'");
			if(result.next()) {
				System.out.println("Sorry, that username already exists. Please try again.");
			}else {
			break;
			}
		}
		
		System.out.println("What do  you want your password to be? (Must be at least 4 characters)");
		String pass = scan.nextLine();
		if(pass.length() < 4) {
			while(pass.length() < 4) {
			System.out.println("Invalid entry. Password not long enough, please try again.");
			pass = scan.nextLine();
			}
		}
	
		Account account = new Account(name, user, pass, 1, 0.0);
		
		if(accountService.addAccount(account)) {
			System.out.println("\n Your account has been successfully created! \n");
		}else {
			System.out.println("Something went wrong!");
		}
	}

	
	
	public static int checkValidAccount(String user, String pass) throws SQLException  {
		Connection connect = ConnectionUtil.getConnection();
		Statement statement = connect.createStatement();
		ResultSet result = statement.executeQuery("SELECT account_level FROM bank_account WHERE user_name = '" + user + "' AND pass_word = '" + pass +"'");
		Account account = new Account();
			
		int accountLevel = 0;
			
		if(result.next()) {
			user = account.getUser();
			pass = account.getPass();
			accountLevel = result.getInt(1);
		}
		
		connect.close();
		return accountLevel;
	
		
	}
	
	private void showOneAccount() {
		System.out.println("What is the name of the account you would like to see?");
		String response = scan.nextLine();
		Account account = accountService.getAccount(response);
		if(account != null) {
			System.out.println(account);
		}else {
			System.out.println("This was not a valid account name, please try again.");
			showOneAccount();
		}
	}
	
	public void showAllAccounts() {
		List<Account> account = accountService.getAllAccounts();
		
		for(Account a:account) {
			System.out.println(a);
		}
	}

}
