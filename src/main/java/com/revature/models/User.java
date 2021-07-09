package com.revature.models;

public class User {
	
	protected String fullName;
	protected String userName;
	protected String phoneNumber;
	protected String address;
	protected String email;
	
	public User(String fullName, String userName, String phoneNumber, String address, String email) {
		super();
		this.fullName = fullName;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
	}

	public User() {
		super();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
