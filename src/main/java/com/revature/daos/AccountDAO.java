package com.revature.daos;

import java.util.List;

import com.revature.models.Account;

public interface AccountDAO {

	List<Account> findAllAccounts();

	Account findByAccountName(String name);


	boolean addAccount(Account account);

}