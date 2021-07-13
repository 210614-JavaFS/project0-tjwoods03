package com.revature.services;

import java.util.List;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.User;

public class UserServices {
	
	private static UserDAO userDAO = new UserDAOImpl();
	
	
	public List<User> getAllUsers(){
		return userDAO.findAllUsers();
	}
	
	public User getUser(String name) {
		return userDAO.findByUsersName(name);
	}
	
	public boolean addUser(User user) {
		return userDAO.addUser(user);
	}

}
