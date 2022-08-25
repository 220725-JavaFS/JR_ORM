package com.revature.services;

import java.util.List;

import com.revature.daos.CustomerDAO;
import com.revature.daos.CustomerDAOImpl;
import com.revature.models.Customers;

public class CustomerServices { //WEEK3 DAY 3 JUnit Unit testing!!
	
	private CustomerDAO customerDao = new CustomerDAOImpl();
	
	public List<Customers> getAllCustomers(){
		return customerDao.getAllCustomers();
	}
	
	public Customers getCustomer(String firstName, String lastName) { //instead of ID, it's by username and password
		return customerDao.getCustomer(firstName, lastName);
	}

	public void registerCustomer(Customers customer) {
		customerDao.registerCustomer(customer);
	}
	
	public void updateCustomer(Customers customer) {
		customerDao.updateCustomer(customer);
	}
	
	public void deleteCustomer(String firstName, String lastName) {
		customerDao.deleteCustomer(firstName, lastName);
	}
	
	
	
	
	public static void main(String[] args) {
		CustomerDAO cust = new CustomerDAOImpl();
		List<Customers> list = cust.getAllCustomers();
		
		System.out.println("testing outside loop");
		System.out.println(list); // it's not retreiving!
		System.out.println("Testing after loop");
	}
}
