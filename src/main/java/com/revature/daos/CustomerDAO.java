package com.revature.daos;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.revature.models.Connectivity;
import com.revature.models.Customers;

// DAO data access object - this is where we define what type of interactions we can have with our database
public interface CustomerDAO {

	//initializes connectivity object in DAOs layer
	public void initializeConnection(Connectivity con);
	
	public String columnNames(Object o);
	public String fieldValues(Object o);
	
	//these use field values and columnNames from upper methods
	public void insertObject(Object o);
	public void updateObject(Object o);
	
	//these are independent from upper methods
	public <T> List<T> getAllObjects(Class<T> clazz);
	public <T> T getObjectById(Class<T> clazz, int id); // similar to bottom
	public <T> T getObjectByFirstName(Class<T> clazz, String firstName); // similar to top
	public void deleteObjectById(Object o, int id);

	//this is from Carolyn's demo
	public Object convertStringToFieldType(String input, Class<?> type)
			throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException;
	
}
