package com.revature.daos;

import java.util.List;

import com.revature.models.Connectivity;
import com.revature.models.Customers;

// DAO data access object - this is where we define what type of interactions we can have with our database
public interface CustomerDAO {

	//undefined at the moment
	//public Customers getCustomersByID(int ID);
	
	public <T> T getObjectById(Class<T> clazz, int id); // fresh to deaf
	
	public Customers getCustomer(String firstName, String lastName);
	
	public Customers getCustomerById(int id);
	
	public void updateCustomerFinal(Object o);
	
	public void insertCustomer(Object o);
	
//	public void registerCustomer(Customers customer);
	
//	public void updateCustomer(Customers customer);

	public void deleteCustomer(String firstName, String lastName);
	
	public String getDataByUsername(String firstName, String columnName);
	
	public String fieldValues(Object o);
	
	public String columnNames(Object o);
	
	
	public void saveConnectionObject(Connectivity con);
	
		


	
	
	
	

}
