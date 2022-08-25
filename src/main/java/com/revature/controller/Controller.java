package com.revature.controller;

import com.revature.daos.CustomerDAOImpl;

public class Controller {

	private CustomerDAOImpl cDao = new CustomerDAOImpl();
	
	public void connectToDatabase(String url, String database, String username, String password) {
		cDao.connectToDatabase(url, database, username, password);
	}
	
	
	
}
