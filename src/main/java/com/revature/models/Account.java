package com.revature.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {

	protected int accountID;
	protected String name;
	protected String user;
	protected String pass;
	protected int level;
	protected double accountBalance;
	
	private static Logger log = LoggerFactory.getLogger(Account.class);
	
	public Account(String name, String user, String pass, int level, double accountBalance) {
		super();
		this.name = name;
		this.user = user;
		this.pass = pass;
		this.level = level;
		if(accountBalance >= 0) {
			this.accountBalance = accountBalance;
		} else {
			this.accountBalance = 0;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(accountBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + accountID;
		result = prime * result + level;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
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
		if (Double.doubleToLongBits(accountBalance) != Double.doubleToLongBits(this.accountBalance))
			return false;
		if (accountID != other.accountID)
			return false;
		if (level != other.level)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
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
		return "Account [accountID=" + accountID + ", name=" + name + ", user=" + user + ", pass=" + pass + ", level="
				+ level + ", accountBalance=" + accountBalance + ", toString()=" + super.toString() + "]";
	}

	
	
}
