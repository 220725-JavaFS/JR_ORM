package com.revature.services;

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
	
	
	
/*	Class<?> c1 = jaribObject.getClass();
	
	Field[] fields = c1.getDeclaredFields();
	
	for(Field f: fields) {
		System.out.println(f);
	}
	
	Method[] methods = c1.getDeclaredMethods();
	
	for(Method m:methods) {
		System.out.println(m);
		
	}
*/	
	System.out.println("-------------------------------------");
	
	Example example = new Example();
	
	cDAO.insertCustomer(jaribObject);
//	cDAO.updateCustomerFinal(jaribObject);
	
	
	
	
	
	
	
	
	}
	
	// this should allocate the sql based on class type (if having more tables)
	public void printClassType(Object o) {
		
		// this will call the sql statement to be saved as a class
		if(o.getClass() == Customers.class) {
			System.out.println("You have picked a customer.");
		}
	/*	if(o.getClass() == Account.class) {
			System.out.println("You have picked an account.");
		}
	*/	
	}
	
	
	/* ---------------AT THE DAOS-----------------------
	
	// these fieldvalues and names displays in order regardless
	public String serialize(Object o) {
		
		Class<?> objectClass = o.getClass();
		
		Field[] fields = objectClass.getDeclaredFields();
		// this is our sql builder
		StringBuilder sqlBuilder = new StringBuilder();
		
		for (Field f: fields) {
			
			String fieldName = f.getName();
			
			String getterName = "get"+fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			
			
			try {
				// obtain the getter method from the class we are mapping
				Method getterMethod = objectClass.getMethod(getterName);
				
				// invoke the method on the object that we are mapping
				Object fieldValue = getterMethod.invoke(o);
				
				System.out.println("Field name = "+fieldName+" and Field value = "+fieldValue);
				
				//sqlBuilder.append(" " );
				
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "the end";
		
	}
	
	*/
	

}
