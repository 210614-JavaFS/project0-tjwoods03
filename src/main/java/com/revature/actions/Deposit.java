package com.revature.actions;

import java.util.*;

public class Deposit implements Transaction{

	private static Scanner scan;
	private static double x;
	private static double balance;
	

	public double getBalance() {
		return balance;
	}
	
	public static double deposit() {
		System.out.println("You have in your account: " + balance);
		System.out.println("How much do yopu want to deposit?");
		x = scan.nextInt();
		if(x >= 0) {
			balance += x;
			return balance;
		} else {
			System.out.println("Invalid input, try again.");
		}
		
		return balance;
		
	}

}
