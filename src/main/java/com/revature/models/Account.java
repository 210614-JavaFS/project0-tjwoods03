package com.revature.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {

	protected int accountID;
	protected String firstName;
	protected String lastName;
	protected String user;
	protected String pass;
	protected String checkingsName;
	protected String savingsName;
	protected double checkingsBalance;
	protected double savingsBalance;
	
	private static Logger log = LoggerFactory.getLogger(Account.class);
	
	public Account(String firstName, String lastName, String user, String pass, String checkingsName, String savingsName, double checkingsBalance, double savingsBalance) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.user = user;
		this.pass = pass;
		this.checkingsName = checkingsName;
		if(checkingsBalance >= 0) {
			this.checkingsBalance = checkingsBalance;
		} else {
			this.checkingsBalance = 0;
			log.warn("You tried to set their balance less than 0 at construction.");
		}
		if(savingsBalance >= 0) {
			this.savingsBalance = savingsBalance;
		} else {
			this.savingsBalance = 0;
			log.warn("You tried to set their balance less than 0 at construction.");
		}
	}
	
	public Account() {
		super();
	}
	
	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCheckingsName() {
		return checkingsName;
	}

	public void setCheckingsName(String accountName) {
		this.checkingsName = accountName;
	}
	
	public String getSavingsName() {
		return savingsName;
	}

	public void setSavingsName(String savingsName) {
		this.savingsName = savingsName;
	}

	public double getCheckingsBalance() {
		return checkingsBalance;
	}

	public void setCheckingsBalance(double checkingsBalance) {
		this.checkingsBalance = checkingsBalance;
	}

	public double getSavingsBalance() {
		return savingsBalance;
	}

	public void setSavingsBalance(double savingsBalance) {
		this.savingsBalance = savingsBalance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountID;
		long temp;
		temp = Double.doubleToLongBits(checkingsBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((checkingsName == null) ? 0 : checkingsName.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		temp = Double.doubleToLongBits(savingsBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((savingsName == null) ? 0 : savingsName.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountID != other.accountID)
			return false;
		if (Double.doubleToLongBits(checkingsBalance) != Double.doubleToLongBits(other.checkingsBalance))
			return false;
		if (checkingsName == null) {
			if (other.checkingsName != null)
				return false;
		} else if (!checkingsName.equals(other.checkingsName))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (Double.doubleToLongBits(savingsBalance) != Double.doubleToLongBits(other.savingsBalance))
			return false;
		if (savingsName == null) {
			if (other.savingsName != null)
				return false;
		} else if (!savingsName.equals(other.savingsName))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [accountID=" + accountID + ", firstName=" + firstName + ", lastName=" + lastName + ", user="
				+ user + ", pass=" + pass + ", checkingsName=" + checkingsName + ", savingsName=" + savingsName
				 + ", checkingsBalance=" + checkingsBalance + ", savingsBalance=" + savingsBalance
				+ ", toString()=" + super.toString() + "]";
	}


	
}
