package com.revature.controller;

import com.revature.daos.CustomerDAOImpl;
import com.revature.models.Connectivity;

public class Controller {

	private CustomerDAOImpl cDao = new CustomerDAOImpl();
	
	
	public void connectToDatabase(String url, String database, String username, String password) {
		Connectivity con = new Connectivity(url, database, username, password);
		cDao.saveConnectionObject(con);
	}
	
	
	
}
