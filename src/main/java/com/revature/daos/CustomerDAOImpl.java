package com.revature.daos;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Customers;
import com.revature.utils.ConnectionUtil;


public class CustomerDAOImpl implements CustomerDAO {
	
	private ConnectionUtil connec = new ConnectionUtil();

	public void connectToDatabase(String url, String database, String username, String password) {
		try {
			connec.getConnection(url, database, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			//log here
		}
	}

	
	// should be from any table, not just customers
	public String columnNames() {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM customers;"; // make it for all tables
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			ResultSetMetaData resultInfo = result.getMetaData();
		
			//gives me all the sql columns name in an arrayList
			StringBuilder builder1 = new StringBuilder();
			for(int i = 2; i<=resultInfo.getColumnCount();i++) {
				String columnName = resultInfo.getColumnName(i);
				builder1.append(columnName+", ");
			}
			String finalColumnName = builder1.substring(0, builder1.length()-2);
		//	System.out.println(finalColumnName);
			return finalColumnName;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String fieldValues(Object o) {
		
		Class<?> objectClass = o.getClass();
		
		Field[] fields = objectClass.getDeclaredFields();
		// this is our sql builder
		StringBuilder valueBuilder = new StringBuilder();
		
		for (Field f: fields) {
			
			String fieldName = f.getName();
			
			String getterName = "get"+fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
								
			try {
				// obtain the getter method from the class we are mapping
				Method getterMethod = objectClass.getMethod(getterName);
				
				
				// invoke the method on the object that we are mapping
				Object fieldValue = getterMethod.invoke(o);
				
	//			System.out.println("Field name = "+fieldName+" and Field value = "+fieldValue);
				
				
				valueBuilder.append(fieldValue+", ");
				
			}catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String finalValues = valueBuilder.substring(0, valueBuilder.length()-2);
	//	System.out.println(finalValues);
		return finalValues;
	}
	
	
	//----------------------SERIALIZED----Not going through (CLOSED CONNECTION ISSUE)------
		public void insertCustomer(Object o) {
			try (Connection conn = ConnectionUtil.getConnection(   )){
				String sql = "\"INSERT INTO customers ("+columnNames()+") VALUES ("+fieldValues(o)+");\"";
				System.out.println(sql);
				Statement statement = conn.createStatement();
				statement.execute(sql);

			}catch(SQLException e) {
				e.printStackTrace();
			}		
		}
		
		//--------------SERIALIZED--------- NOT going through (Closed CONNECTION ISSUE)----
		public void updateCustomerFinal(Object o) {
			try (Connection conn = ConnectionUtil.getConnection()){
				String[] columnArray = columnNames().split(", ");
				String[] fieldArray = fieldValues(o).split(", ");
				StringBuilder strBuilder = new StringBuilder();
				for (int i = 0; i<columnArray.length;i++) {
			
					strBuilder.append(columnArray[i]+" = "+fieldArray[i]+", ");
				}
				String finalString = strBuilder.substring(0,strBuilder.length()-2);
				String sql = "\"UPDATE customers SET "+finalString+" WHERE customers."+columnArray[2]+" = "+fieldArray[2]+" AND customers."+columnArray[3]+" = "+fieldArray[3]+";\"";
				System.out.println(sql);
	
				Statement statement = conn.createStatement();
				statement.execute(sql);

			}catch(SQLException e) {
				e.printStackTrace();
			}		
		}


	// --------------INCOMPLETE turning DATABASE INTO OBJECTS--Might just leave as is-----------------------

	public List<Customers> getAllCustomers() {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM customers;"; //returns all info from both tables
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
		
			List<Customers> customerList = new ArrayList<>();
			
			while(result.next()) { // instead of if statement, the while statement will keep returning the next result of the queue.
				Customers customer = new Customers(
						result.getString("initial_date"),
						result.getString("customer_source"),
						result.getString("first_name"),
						result.getString("last_name"),
						result.getString("phone"),
						result.getString("email"),
						result.getString("occupation"),
						result.getInt("times_trained"),
						result.getString("train_date"),
						result.getString("notes")
						);
			
			}
			
			return customerList;
		}catch(SQLException   e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public Customers getCustomer(String firstName, String lastName) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM customers WHERE first_name = ? AND last_name = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, firstName); // this is where SQL injection is checked for. Tim used the example of String though.
			statement.setString(2, lastName);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				Customers cust = new Customers(
						result.getString("initial_date"),
						result.getString("customer_source"),
						result.getString("first_name"),
						result.getString("last_name"),
						result.getString("phone"),
						result.getString("email"),
						result.getString("occupation"),
						result.getInt("times_trained"),
						result.getString("train_date"),
						result.getString("notes")
						);
				return cust;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	
	// ----------------- this is being replaced by THE INSERT CUSTOMER METHOD---------------
	@Override
	public void registerCustomer(Customers customer) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO customers (initial_date, customer_source, first_name, last_name, "
					+ "phone, email, occupation, times_trained, train_date, notes)"
					+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int count = 0;
			statement.setString(++count, customer.getInitialDate());
			statement.setString(++count, customer.getCustomerSource());
			statement.setString(++count, customer.getFirstName());
			statement.setString(++count, customer.getLastName());
			statement.setString(++count, customer.getPhone());
			statement.setString(++count, customer.getEmail());
			statement.setString(++count, customer.getOccupation());
			statement.setInt(++count, customer.getTimesTrained());
			statement.setString(++count, customer.getTrainedDate());
			statement.setString(++count, customer.getNotes());
			
			statement.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateCustomer(Customers customer) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE customers SET initial_date=?, customer_source =?, first_name =?, last_name =?,\r\n"
					+ "phone =?, email=?, occupation=?, times_trained=?,\r\n"
					+ "train_date=?, notes=? WHERE customers.first_name = ? AND customers.last_name = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int count = 0;
			statement.setString(++count, customer.getInitialDate());
			statement.setString(++count, customer.getCustomerSource());
			statement.setString(++count, customer.getFirstName());
			statement.setString(++count, customer.getLastName());
			statement.setString(++count, customer.getPhone());
			statement.setString(++count, customer.getEmail());
			statement.setString(++count, customer.getOccupation());
			statement.setInt(++count, customer.getTimesTrained());
			statement.setString(++count, customer.getTrainedDate());
			statement.setString(++count, customer.getNotes());
			statement.setString(++count, customer.getFirstName());
			statement.setString(++count, customer.getLastName());
			
			statement.execute();	
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteCustomer(String firstName, String lastName) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM customers WHERE customers.first_name =? AND customers.last_name =?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, firstName); // this is where SQL injection is checked for. Tim used the example of String though.
			statement.setString(2, lastName);
			
			ResultSet result = statement.executeQuery();	
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	// this will be implemented when the client puts getToWork/jarib/notes
	@Override
	public String getDataByUsername(String firstName, String columnName) { 
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT ? FROM customers WHERE first_name = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, columnName); 
			statement.setString(1, firstName);
			
			ResultSet result = statement.executeQuery();
			String temp = ""; 
			if (result.next()) {
				temp = result.getString("columnName"); // ----- NOTE: columnName can't be executed. FIND A SOLUTION! this will give an error!
			}
			return temp;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public Customers getCustomerById(int id) {
		
			try (Connection conn = ConnectionUtil.getConnection()){
				
				String sql = "SELECT * FROM customers WHERE id= ?;";
				PreparedStatement statement = conn.prepareStatement(sql);
				
				statement.setInt(1, id); // this is where SQL injection is checked for. Tim used the example of String though.
				
				ResultSet result = statement.executeQuery();
				
				if(result.next()) {
					Customers cust = new Customers(
							result.getString("initial_date"),
							result.getString("customer_source"),
							result.getString("first_name"),
							result.getString("last_name"),
							result.getString("phone"),
							result.getString("email"),
							result.getString("occupation"),
							result.getInt("times_trained"),
							result.getString("train_date"),
							result.getString("notes")
							);
					return cust;
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	
	
	
	
	//Test with Tim for MONDAY!
	public static void main(String[] args) {
		CustomerDAOImpl cDao = new CustomerDAOImpl();
		List<Customers> list = cDao.getAllCustomers();
		System.out.println(list);
		
		}


	


	
	
	
	
}