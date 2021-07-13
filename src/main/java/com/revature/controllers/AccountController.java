package com.revature.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.revature.services.AccountServices;
import com.revature.services.UserServices;
import com.revature.daos.ConnectionUtil;
import com.revature.models.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


//main way of using application
public class AccountController{

	private static AccountServices accountService = new AccountServices();
	private static Logger log = LoggerFactory.getLogger(AccountController.class);
	private static AccountController account = new AccountController();
	private static UserController userController = new UserController();
	private static UserServices userService = new UserServices();
	private ArrayList<Account> addAccounts = new ArrayList<Account>();
	
	
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
			boolean accountActive = AccountController.checkActiveAccount(userName, password);

			while(accountLevel > 0) {
				//Customer login
				System.out.println("\n *****Login Successful***** ");
				if(accountActive) {
				while(accountLevel == 1) {
				System.out.println("\nWelcome, how can we help you?");
				System.out.println("You can: \n"
						+ "1) View balances. \n"
						+ "2) Transfer. \n"
						+ "3) Make a withdrawl. \n"
						+ "4) Make a deposit. \n"
						+ "5) Update personal information. \n"
						+ "6) View personal information. \n"
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
					
					//Transfer money between accounts. Can only do so if have more than one account
					case "2":
						//list current accounts with balances
						try(Connection conn = ConnectionUtil.getConnection()){
						System.out.println("How would you like to transfer money? \n"
								+ "1) Checkings to Savings \n"
								+ "2) Savings to Checkings");
						String transfer = scan.nextLine();
						switch(transfer) {
						case "1":
							result = statement.executeQuery("SELECT checkings_balance, savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
							if(result.next()) {
							Double checkingBalance = result.getDouble(1);
							Double savingsBalance =  result.getDouble(2);
							
							System.out.println("How much would you like to transfer? You currently have $" + checkingBalance + " in your checking account.");
							double amount = scan.nextDouble();
							scan.nextLine();
							while(amount > checkingBalance) {
								System.out.println("You can't take out more than you currently have. Please try again.");
								amount = scan.nextDouble();
							}
		
							statement.executeUpdate("UPDATE bank_account SET checkings_balance = " + (checkingBalance - amount) + " WHERE user_name = '" + userName +"';");
							statement.executeUpdate("UPDATE bank_account SET savings_balance = " + (savingsBalance + amount) + " WHERE user_name = '" + userName +"';");
							ResultSet getCheckingsBalance = statement.executeQuery("SELECT checkings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								while(getCheckingsBalance.next()) {
									Double currentBalance = getCheckingsBalance.getDouble(1);
									System.out.println("You now have $" + currentBalance + " in your checking account.");
								}
								ResultSet getSavingsBalance = statement.executeQuery("SELECT savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								while(getSavingsBalance.next()) {
									Double currentBalance = getSavingsBalance.getDouble(1);
									System.out.println("You now have $" + currentBalance + " in your savings account.");
								}
							}
							break;
						case "2":
							result = statement.executeQuery("SELECT checkings_balance, savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
							if(result.next()) {
							Double checkingBalance = result.getDouble(1);
							Double savingsBalance =  result.getDouble(2);
							
							System.out.println("How much would you like to transfer? You currently have $" + savingsBalance + " in your checking account.");
							double amount = scan.nextDouble();
							scan.nextLine();
							while(amount > savingsBalance) {
								System.out.println("You can't take out more than you currently have. Please try again.");
								amount = scan.nextDouble();
							}
		
							statement.executeUpdate("UPDATE bank_account SET savings_balance = " + (savingsBalance - amount) + " WHERE user_name = '" + userName +"';");
							statement.executeUpdate("UPDATE bank_account SET checkings_balance = " + (checkingBalance + amount) + " WHERE user_name = '" + userName +"';");
							ResultSet getCheckingsBalance = statement.executeQuery("SELECT checkings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								while(getCheckingsBalance.next()) {
									Double currentBalance = getCheckingsBalance.getDouble(1);
									System.out.println("You now have $" + currentBalance + " in your checking account.");
								}
								ResultSet getSavingsBalance = statement.executeQuery("SELECT savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								while(getSavingsBalance.next()) {
									Double currentBalance = getSavingsBalance.getDouble(1);
									System.out.println("You now have $" + currentBalance + " in your savings account.");
								}
							}
							break;
						default:
							System.out.println("Invalid input, please try again.");
							break;
						}
						}catch(SQLException e) {
							e.printStackTrace();
						} 
						
						break;
					//Withdraw money from account
					case "3":
						try(Connection conn = ConnectionUtil.getConnection()){
							System.out.println("What account would you like to withdraw from? \n"
									+ "1) Checking. \n"
									+ "2) Savings.");
							String outOf = scan.nextLine();
							switch(outOf) {
							case "1":
								result = statement.executeQuery("SELECT checkings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								if(result.next()) {
								Double balance = result.getDouble(1);
								System.out.println("How much would you like to deposit? You currently have $" + balance + " in your checking account.");
								double amount = scan.nextDouble();
								scan.nextLine();
								while(amount > balance) {
									System.out.println("You can't take out more than you currently have. Please try again.");
									amount = scan.nextDouble();
								}
								statement.executeUpdate("UPDATE bank_account SET checkings_balance = " + (balance - amount) + " WHERE user_name = '" + userName +"';");
								ResultSet getCurrentBalance = statement.executeQuery("SELECT checkings_balance FROM bank_account WHERE user_name = '" + userName + "';");
									while(getCurrentBalance.next()) {
										Double currentBalance = getCurrentBalance.getDouble(1);
										System.out.println("You now have $" + currentBalance + " in your checking account.");
									}
								}
								break;
							case "2":
								result = statement.executeQuery("SELECT savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								if(result.next()) {
								Double balance = result.getDouble(1);
								System.out.println("How much would you like to deposit? You currently have $" + balance + " in your savings account.");
								double amount = scan.nextDouble();
								scan.nextLine();
								while(amount > balance) {
									System.out.println("You can't take out more than you currently have. Please try again.");
									amount = scan.nextDouble();
								}
								statement.executeUpdate("UPDATE bank_account SET savings_balance = " + (balance - amount) + " WHERE user_name = '" + userName +"';");
								ResultSet getCurrentBalance = statement.executeQuery("SELECT savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
									while(getCurrentBalance.next()) {
										Double currentBalance = getCurrentBalance.getDouble(1);
										System.out.println("You now have $" + currentBalance + " in your savings account.");
									}
								}
								break;
							default:
								System.out.println("Invalid input, please try again.");
								break;
							}
							}catch(SQLException e) {
								e.printStackTrace();
							} 
						break;
					//Deposit money into account
					case "4":
						try(Connection conn = ConnectionUtil.getConnection()){
							System.out.println("What account would you like to deposit into? \n"
									+ "1) Checking. \n"
									+ "2) Savings.");
							String into = scan.nextLine();
							switch(into) {
							case "1":
								result = statement.executeQuery("SELECT checkings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								if(result.next()) {
								Double balance = result.getDouble(1);
								System.out.println("How much would you like to deposit? You currently have $" + balance + " in your checking account.");
								double amount = scan.nextDouble();
								scan.nextLine();
								while(amount < 0) {
									System.out.println("You can't enter a negative number. Please try again.");
									amount = scan.nextDouble();
								}
								statement.executeUpdate("UPDATE bank_account SET checkings_balance = " + (balance + amount) + " WHERE user_name = '" + userName +"';");
								ResultSet getCurrentBalance = statement.executeQuery("SELECT checkings_balance FROM bank_account WHERE user_name = '" + userName + "';");
									while(getCurrentBalance.next()) {
										Double currentBalance = getCurrentBalance.getDouble(1);
										System.out.println("You now have $" + currentBalance + " in your checking account.");
									}
								}
								break;
							case "2":
								result = statement.executeQuery("SELECT savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								if(result.next()) {
								Double balance = result.getDouble(1);
								System.out.println("How much would you like to deposit? You currently have $" + balance + " in your savings account.");
								double amount = scan.nextDouble();
								scan.nextLine();
								while(amount < 0) {
									System.out.println("You can't enter a negative number. Please try again.");
									amount = scan.nextDouble();
								}
								statement.executeUpdate("UPDATE bank_account SET savings_balance = " + (balance + amount) + " WHERE user_name = '" + userName +"';");
								ResultSet getCurrentBalance = statement.executeQuery("SELECT savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
									while(getCurrentBalance.next()) {
										Double currentBalance = getCurrentBalance.getDouble(1);
										System.out.println("You now have $" + currentBalance + " in your savings account.");
									}
								}
								break;
							default:
								System.out.println("Invalid input, please try again.");
								break;
							}
							}catch(SQLException e) {
								e.printStackTrace();
							} 
						break;
					//Update or add personal information
					case "5":
						userController.updateUserInfo();
						break;
					//View personal info, returns null values if user hasn't added anything yet
					case "6":
						userController.viewUser();
						break;
					//Leave program
					case "7":
						System.out.println("\n  *****Thank you for visiting the Bank of Revature!***** \n");
						System.exit(0);
					default:
						System.out.println("Invalid input, please try again.");
						break;
					}
				} //end of customer while statement
				}//end of if customer account is activated
				else {
					System.out.println("Your account is not activated. Please wait until its activated.");
					System.exit(0);
				}
				//Employee login
				while(accountLevel == 2) {
					System.out.println("Employee welcome! \n");
					while(true){
					System.out.println("What would you like to do? \n"
							+ "1) Activate/deactivate a customers account. \n"
							+ "2) View an accounts balance. \n"
							+ "3) View an accounts information. \n"
							+ "4) View a users information. \n"
							+ "5) Exit application.");
					String response = scan.nextLine();
					
					switch(response) {
						case "1":
							System.out.println("What is the username of the account you'd like to activate/deactivate.");
							String username = scan.nextLine();
							
							result = statement.executeQuery("SELECT * FROM user_info WHERE username = '" + username + "'");
							if(result.next()) {
								System.out.println("Thank you. \n");
							}else {
							System.out.println("Sorry that was username doesn't match anything in our system. Please try again.");
							name = scan.nextLine();
							}
							
							System.out.println("Would you like to: \n"
									+ "1) Activate the account \n"
									+ "2) Deactivate the account.");
							String choice = scan.nextLine();
							switch(choice) {
							case "1":
								try(Connection conn = ConnectionUtil.getConnection()){
									statement.executeUpdate("UPDATE user_info SET active = TRUE WHERE username = '" + username +"';");
									}catch(SQLException e) {
										e.printStackTrace();
									}
								break;
							case "2":
								try(Connection conn = ConnectionUtil.getConnection()){
									statement.executeUpdate("UPDATE user_info SET active = FALSE WHERE username = '" + username +"';");
									}catch(SQLException e) {
										e.printStackTrace();
									}
								break;
							default:
								System.out.println("Invalid input, please try again.");
								break;
							}
							break;
						case "2":
							System.out.println("What users account do you want to view?");
							userName = scan.nextLine();
							try(Connection conn = ConnectionUtil.getConnection()){
								ResultSet getBalance = statement.executeQuery("SELECT checkings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								while(getBalance.next()) {
								Double balance = getBalance.getDouble(1);
								System.out.println("They currently have $" +balance + " in their checkings account.");
								}
								getBalance = statement.executeQuery("SELECT savings_balance FROM bank_account WHERE user_name = '" + userName + "';");
								while(getBalance.next()) {
								Double balance = getBalance.getDouble(1);
								System.out.println("\nThey currently have $" +balance + " in their savings account. \n");
								}
								}catch(SQLException e) {
									e.printStackTrace();
								}
							break;
						case "3":
							showOneAccount();
							break;
						case "4":
							userController.viewUser();
							break;
						case "5":
							System.exit(0);
						default:
							System.out.println("Invalid input, please try again.");
							break;
					}
					}
				} 
				
				//Admin login
				while(accountLevel == 3){
					System.out.println("Admin welcome");
					System.exit(0);
				}
				while(accountLevel != 1 | accountLevel != 2 | accountLevel != 3) {
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
		System.out.println("What is your first name?");
		String firstName = scan.nextLine();
		System.out.println("What is your last name?");
		String lastName = scan.nextLine();
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
			result = statement.executeQuery("SELECT * FROM user_info WHERE username = '" + user + "'");
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
		
		User userAccount = new User(firstName, lastName, user, pass, "", "", "", 1, false);
		
		if(userService.addUser(userAccount)) {
			System.out.println("\n Your account has been successfully created! \n");
		}else {
			System.out.println("Something went wrong!");
		}
	}

	
	
	public static int checkValidAccount(String user, String pass) throws SQLException  {
		Connection connect = ConnectionUtil.getConnection();
		Statement statement = connect.createStatement();
		ResultSet result = statement.executeQuery("SELECT account_level FROM user_info WHERE username = '" + user + "' AND pass_word = '" + pass +"'");
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
	
	public static boolean checkActiveAccount(String user, String pass) throws SQLException{
		Connection connect = ConnectionUtil.getConnection();
		Statement statement = connect.createStatement();
		ResultSet result = statement.executeQuery("SELECT active FROM user_info WHERE username = '" + user + "' AND pass_word = '" + pass +"'");
		
		User activeUser = new User();
		
		boolean activated = false;
		if(result.next()) {
			user = activeUser.getUserName();
			pass = activeUser.getPass();
			activated = result.getBoolean(1);
			
		}
		
		return activated;
		
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
