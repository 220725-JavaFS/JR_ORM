package com.revature.services;

import com.revature.controller.Controller;
import com.revature.daos.CustomerDAOImpl;
import com.revature.models.Customers;

public class Example {
	
	public static void main(String[] args) {
		
	Customers jaribObject = new Customers();
	Customers emptyObject = new Customers();
	ORMService ormy = new ORMService();
	CustomerDAOImpl cDAO = new CustomerDAOImpl();
	
	jaribObject.setInitialDate("02/22/22");
	jaribObject.setCustomerSource("referall by Jarib");
	jaribObject.setFirstName("Jarib");
	jaribObject.setLastName("Rivas");
	jaribObject.setPhone("907-978-9973");
	jaribObject.setEmail("bossjarib@Yahoo.com");
	jaribObject.setOccupation("Petroleum engineer");
	jaribObject.setTimesTrained(10);
	jaribObject.setTrainedDate("02/55/55");
	jaribObject.setNotes("Great job");
	
	
	
	
	
	}
	
}
