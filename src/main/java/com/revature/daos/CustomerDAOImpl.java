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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.modelsORM.Connectivity;
import com.revature.utils.ConnectionUtil;


public class CustomerDAOImpl<T> implements CustomerDAO {
	
	private Connectivity con;
	
	private static Logger log = LoggerFactory.getLogger(CustomerDAOImpl.class); //need to configure 
	
	
	public void initializeConnection(Connectivity con) {
		this.con = con;
		System.out.println("con "+con); // this prints out successfully
		log.info("Initialized connectivity object in DAOs");
	}

	
	// should be from any table, not just customers
	public String columnNames(Object o) {
		/*
		try (Connection conn = ConnectionUtil.getConnection(con)){
			Class<?> objectClass = o.getClass();
			String [] str = objectClass.getName().split("\\.");
			String className = str[str.length-1];
			
			String sql = "SELECT * FROM "+className.toLowerCase()+";"; // make it for all tables
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			ResultSetMetaData resultInfo = result.getMetaData();
		
			//gives me all the sql columns name in an arrayList - starts after the SERIAL Initial Key @Column1
			StringBuilder builder1 = new StringBuilder();
			for(int i = 2; i<=resultInfo.getColumnCount();i++) {
				String columnName = resultInfo.getColumnName(i);
				builder1.append(columnName+", ");
			}
			String finalColumnName = builder1.substring(0, builder1.length()-2); // this gives me the string column names separated by comma in between
			return finalColumnName;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		*/
		return null;
	}
	
	
	public String fieldValues(Object o) {
		/*
		Class<?> objectClass = o.getClass();
		
		System.out.println(o.getClass().toString()); //--------------testing
		
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
				
				
				valueBuilder.append("'"+fieldValue+"', ");
				
			}catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String finalValues = valueBuilder.substring(0, valueBuilder.length()-2);
	//	System.out.println(finalValues);
		return finalValues;
		*/
		return null;
	}
	
	
	public void insertObject(Object o) { 
		try (Connection conn = ConnectionUtil.getConnection(con)){
			
			// 1) gets all column names
			
			Class<?> objectClass = o.getClass();
			String [] str = objectClass.getName().split("\\.");
			String className = str[str.length-1];
			
			String sql = "SELECT * FROM "+className.toLowerCase()+";"; // make it for all tables
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			ResultSetMetaData resultInfo = result.getMetaData();
		
			//gives me all the sql columns name in an arrayList - starts after the SERIAL Initial Key @Column1
			StringBuilder builder1 = new StringBuilder();
			for(int i = 2; i<=resultInfo.getColumnCount();i++) {
				String columnName = resultInfo.getColumnName(i);
				builder1.append(columnName+", ");
			}
			String finalColumnName = builder1.substring(0, builder1.length()-2); // this gives me the string column names separated by comma in between
			
			
			
			//------------------------------------------
			
			
			
			// 2) gets all fieldvalues
			
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
					
					
					valueBuilder.append("'"+fieldValue+"', ");
					
				}catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String finalValues = valueBuilder.substring(0, valueBuilder.length()-2);

			
			
			
			//-------------------------------------------------------------------------------
			
			// 3) inserts the object
			
			String [] str2 = objectClass.getName().split("\\.");
			String classNameFinal = str2[str.length-1];
			
			String sqlF = "INSERT INTO "+classNameFinal.toLowerCase()+" ("+finalColumnName+") VALUES ("+finalValues+");"; // calls fieldValues and columnNames method
			
			Statement statementF = conn.createStatement();
			statementF.execute(sqlF);
			log.info("insertObject successful");
			
		}catch(SQLException e) {
			e.printStackTrace();
			log.error("insertObject no good");
		}		
	}
	
	
	
	/*
		public void insertObject(Object o) { 
			try (Connection conn = ConnectionUtil.getConnection(con)){
				
				Class<?> objectClass = o.getClass();
				System.out.println("this is objectClass above: "+objectClass);
				String [] str = objectClass.getName().split("\\.");
				String className = str[str.length-1];
				System.out.println(className);
				
				String sql = "INSERT INTO "+className.toLowerCase()+" ("+columnNames(o)+") VALUES ("+fieldValues(o)+");"; // calls fieldValues and columnNames method
				System.out.println(sql);
				
				Statement statement = conn.createStatement();
				statement.execute(sql);
				log.info("insertObject successful");
				
			}catch(SQLException e) {
				e.printStackTrace();
				log.error("insertObject no good");
			}		
		}
		*/
		
		
		
		public void updateObject(Object o) {
			
			try (Connection conn = ConnectionUtil.getConnection(con)){
				
				// 1) gets all column names
				
				Class<?> objectClass = o.getClass();
				String [] str = objectClass.getName().split("\\.");
				String className = str[str.length-1];
				
				String sql = "SELECT * FROM "+className.toLowerCase()+";"; // make it for all tables
				Statement statement = conn.createStatement();
				ResultSet result = statement.executeQuery(sql);
				ResultSetMetaData resultInfo = result.getMetaData();
			
				//gives me all the sql columns name in an arrayList - starts after the SERIAL Initial Key @Column1
				StringBuilder builder1 = new StringBuilder();
				for(int i = 2; i<=resultInfo.getColumnCount();i++) {
					String columnName = resultInfo.getColumnName(i);
					builder1.append(columnName+", ");
				}
				String finalColumnName = builder1.substring(0, builder1.length()-2); // this gives me the string column names separated by comma in between
				
				//------------------------------------------
				
				// 2) gets all fieldvalues
				
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
						
						
						valueBuilder.append("'"+fieldValue+"', ");
						
					}catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String finalValues = valueBuilder.substring(0, valueBuilder.length()-2);

				
				
				String[] columnArray = finalColumnName.split(", "); // calls columnNames method
				String[] fieldArray = finalValues.split(", "); // calls fieldValues method
				
				StringBuilder strBuilder = new StringBuilder();
				
				for (int i = 0; i<columnArray.length;i++) {
			
					strBuilder.append(columnArray[i]+" = "+fieldArray[i]+", ");
				}
				String finalString = strBuilder.substring(0,strBuilder.length()-2);
				String sql2 = "UPDATE "+className+" SET "+finalString+" WHERE "+className+"."+columnArray[2]+" = "+fieldArray[2]+" AND "+className+"."+columnArray[3]+" = "+fieldArray[3]+";";
				System.out.println(sql2);
	
				Statement statement2 = conn.createStatement();
				statement2.execute(sql2);

			}catch(SQLException e) {
				e.printStackTrace();
			}		
			
		}

		
	public <T> List<T> getAllObjects(Class<T> clazz) {
		try (Connection conn = ConnectionUtil.getConnection(con)){
			
			List<T> objectList = new ArrayList<T>();
		
			String[] clazzArray = clazz.getName().split("\\.");
			String clazzName = clazzArray[clazzArray.length-1];
			
			String sql = "SELECT * FROM "+clazzName.toLowerCase()+";"; 
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) { 
				try {
										
					T newObject = clazz.getDeclaredConstructor().newInstance();
					
					Field[] fields = newObject.getClass().getDeclaredFields();
					System.out.println(fields.length + "these are the fields");
					
					for (Field f : fields) {
												
						String fieldName = f.getName();
	
						String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						try {
							Class<?> setterString = clazz.getDeclaredField(fieldName).getType();
	
							Method setter = clazz.getMethod(setterName, setterString);
		
							Object fieldType = convertStringToFieldType(result.getString(fieldName), setterString);
	
							// sets values to object via for each loop 
							setter.invoke(newObject, fieldType);
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
							System.out.println("NoSuchField exception brodie");
							return null;
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
							System.out.println("NoSuchMethodException exception brodie");
							return null;
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							System.out.println("IllegalAccessException exception brodie");
							return null;
						} catch (InvocationTargetException | InstantiationException e) {
							e.printStackTrace();
							System.out.println("InvocationTargetException exception brodie");
							return null;
						}
					}
					    
					objectList.add(newObject);
					
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					log.error("getAllObjects unsuccessful");
					System.out.println("The code is going through here???");
					e.printStackTrace();
				}
			}
			
				log.info("getAllObjects successful - could? it be this huahakdi");
				System.out.println(objectList);
				
				return objectList;
				
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("getAllObjects unsuccessful");
			return null;
		}
	}

	
	
	@Override
	public <T> T getObjectById(Class<T> clazz, int id) {
		try (Connection conn = ConnectionUtil.getConnection(con)){
					
			String[] clazzArray = clazz.getName().split("\\.");
			String clazzName = clazzArray[clazzArray.length-1];
			
			String sql = "SELECT * from "+clazzName.toLowerCase()+" WHERE "+clazzName.toLowerCase()+"."+"id = "+id+";";	
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) { 
				try {
										
					T newObject = clazz.getDeclaredConstructor().newInstance();
					
					Field[] fields = newObject.getClass().getDeclaredFields();
					System.out.println(fields.length + "these are the fields");
					
					for (Field f : fields) {
												
						String fieldName = f.getName();
	
						String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						try {
							Class<?> setterString = clazz.getDeclaredField(fieldName).getType();
	
							Method setter = clazz.getMethod(setterName, setterString);
		
							Object fieldType = convertStringToFieldType(result.getString(fieldName), setterString);
	
							// sets values to object via for each loop 
							setter.invoke(newObject, fieldType);
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
							System.out.println("NoSuchField exception brodie");
							return null;
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
							System.out.println("NoSuchMethodException exception brodie");
							return null;
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							System.out.println("IllegalAccessException exception brodie");
							return null;
						} catch (InvocationTargetException | InstantiationException e) {
							e.printStackTrace();
							System.out.println("InvocationTargetException exception brodie");
							return null;
						}
					}
					    
					return newObject;
					
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					log.error("getAllObjects unsuccessful");
					System.out.println("The code is going through here???");
					e.printStackTrace();
				}
			}				
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("getAllObjects unsuccessful");
			return null;
		}
		return null;
	}
	
	
	
	//-----------------------------------------oiginal--------------
	
	
	public void deleteObjectById(Object o, int id) {
		try(Connection conn = ConnectionUtil.getConnection(con)) {		
			
			String[] array = o.getClass().getName().split("\\."); 
			String clazzName = array[array.length-1];
			
			String sql = "DELETE FROM "+clazzName+" WHERE "+clazzName+".id = "+id+";";
	
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	
	public <T> T getObjectByFirstName(Class<T> clazz, String firstName) {
		
		try (Connection conn = ConnectionUtil.getConnection(con)){
			
			String[] clazzArray = clazz.getName().split("\\.");
			String clazzName = clazzArray[clazzArray.length-1];
			
			String sql = "SELECT * from "+clazzName.toLowerCase()+" WHERE "+clazzName.toLowerCase()+"."+"first_name = "+firstName+";";	
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) { 
				try {
										
					T newObject = clazz.getDeclaredConstructor().newInstance();
					
					Field[] fields = newObject.getClass().getDeclaredFields();
					System.out.println(fields.length + "these are the fields");
					
					for (Field f : fields) {
												
						String fieldName = f.getName();
	
						String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						try {
							Class<?> setterString = clazz.getDeclaredField(fieldName).getType();
	
							Method setter = clazz.getMethod(setterName, setterString);
		
							Object fieldType = convertStringToFieldType(result.getString(fieldName), setterString);
	
							// sets values to object via for each loop 
							setter.invoke(newObject, fieldType);
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
							System.out.println("NoSuchField exception brodie");
							return null;
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
							System.out.println("NoSuchMethodException exception brodie");
							return null;
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							System.out.println("IllegalAccessException exception brodie");
							return null;
						} catch (InvocationTargetException | InstantiationException e) {
							e.printStackTrace();
							System.out.println("InvocationTargetException exception brodie");
							return null;
						}
					}
					    
					return newObject;
					
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					log.error("getAllObjects unsuccessful");
					System.out.println("The code is going through here???");
					e.printStackTrace();
				}
			}				
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("getAllObjects unsuccessful");
			return null;
		}
		return null;
	}
	
	/*
	@Override
	public <T> T getObjectByFirstName(Class<T> clazz, String firstName) {
		
		try(Connection conn = ConnectionUtil.getConnection(con)){
			String[] array = clazz.getName().split("\\.");
			String className = array[array.length - 1];
			String sql = "SELECT * from "+className.toLowerCase()+" WHERE "+className.toLowerCase()+"."+"first_name = "+firstName+";";	
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
						
			while(result.next()) { 
				try {
					T object = clazz.getDeclaredConstructor().newInstance();
				
					Field[] fields = clazz.getFields();
					for (Field field : fields) {
	
						String fieldName = field.getName();
	
						String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						try {
							Class<?> setterString = clazz.getDeclaredField(fieldName).getType();
	
							Method setterMethod = clazz.getMethod(setterName, setterString);
	
							Object fieldType = convertStringToFieldType(result.getString(fieldName), setterString);
	
							setterMethod.invoke(object, fieldType);
	
						} catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
							e.printStackTrace();
						}
					}
					log.info("returned object successfully");
					return object;
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
					e1.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return null;
	}
		*/
	
	public Object convertStringToFieldType(String input, Class<?> type)
			throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		switch (type.getName()) {
		case "byte":
			return Byte.valueOf(input);
		case "short":
			return Short.valueOf(input);
		case "int":
			return Integer.valueOf(input);
		case "long":
			return Long.valueOf(input);
		case "double":
			return Double.valueOf(input);
		case "float":
			return Float.valueOf(input);
		case "boolean":
			return Boolean.valueOf(input);
		case "java.lang.String":
			return input;
		default:
			return type.getDeclaredConstructor().newInstance();
		}
	}
	
	
	
		
}