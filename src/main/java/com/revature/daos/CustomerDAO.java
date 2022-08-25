package com.revature.daos;

import java.util.List;

import com.revature.models.Customers;

// DAO data access object - this is where we define what type of interactions we can have with our database
public interface CustomerDAO {

	//undefined at the moment
	//public Customers getCustomersByID(int ID);
	
	List<Customers> getAllCustomers();
	
	public Customers getCustomer(String firstName, String lastName);
	
	public Customers getCustomerById(int id);
	
	public void registerCustomer(Customers customer);
	
	public void updateCustomer(Customers customer);

	public void deleteCustomer(String firstName, String lastName);
	
	public String getDataByUsername(String firstName, String columnName);
	
	public String fieldValues(Object o);
	
	public String columnNames();
	
	public void insertCustomer(Object o);
	
		


	
	
	
	

}
