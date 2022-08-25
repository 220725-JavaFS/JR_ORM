package com.revature.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class ORMService {
	
	
	
	//This will take in a single line JSON string
	public <T> T deSerialize(String input, Class<T> clazz) {
        if(input==null || input.equals("")){
            return null;
        }
        
        // remove the curly brackets from the JSON string
        String partialJson = input.substring(1,input.length()-1);
        // split the remaining string by the comma to get key value pair string pairs 
        String[] keyValueStrings = partialJson.split(",");
        
        // declare an object to be created
        T newObject = null;

        try {
        	// create a new instance of the class being constructed
            newObject = clazz.getDeclaredConstructor().newInstance();
        
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        // we need to extract the field name and field value from each string key value pair 
        for(String s: keyValueStrings){
            // splitting a key value pair (ex. "name" : "Sally") by the colon should give us an array of size 2 ( ["name":"Sally"] )
        	String[] keyValueArr = s.split(":");
            if(keyValueArr.length != 2){
            	// if we don't get an array of size 2, the json is formatted incorrectly 
            		// here we tell the developer why the method fails with a custom exception
                throw new JsonMappingException("Improperly formatted JSON");
            }
            
            // first we deal with the field name - trim the whitespace and remove the quotes
            String keyString = keyValueArr[0].trim();
            keyString = keyString.substring(1, keyString.length()-1);
            
            // next the value - we also trim the whitespace and remove the quotes
            String valueString = keyValueArr[1].trim();
            valueString = valueString.substring(1, valueString.length()-1);
//            System.out.println("key string: "+keyString + "   - value string: "+valueString );

            //obtain each setter method we need to set the values
            String setterName = "set"+keyString.substring(0,1).toUpperCase() + keyString.substring(1);
            try {
                // getting the type of the setter parameter, based on the field type
                Class<?> setterParamType = clazz.getDeclaredField(keyString).getType();

                // obtain the setter method using the setter name and setter parameter type
                Method setter = clazz.getMethod(setterName, setterParamType);

                // below we define a utility method to convert the string field value to the appropriate type for the field  
                Object fieldValue = convertStringToFieldType(valueString, setterParamType);

                // we invoke the setter to populate the field of the object that's being created
                setter.invoke(newObject,fieldValue);
                
            } catch (NoSuchFieldException  e) {
                throw new JsonMappingException(keyString+" field does not exist in class "+clazz);
            } catch ( NoSuchMethodException e){
                throw new JsonMappingException("no valid setter for: "+keyString);
            } catch (IllegalAccessException e) {
                throw new JsonMappingException("cannot access setter for: "+keyString);
            } catch (InvocationTargetException | InstantiationException e) {
                throw new JsonMappingException("issue invoking setter for: "+keyString);
            }

        }
        // once we iterate over each field, the object we're attempting to create is fully populated and ready to return 
        return newObject;
    }
	
	
	
	public Object convertStringToFieldType(String input, Class<?> type) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        switch(type.getName()){
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
            case "java.time.LocalDate":
                return LocalDate.parse(input);
            default:
                return type.getDeclaredConstructor().newInstance();
        }
    }

}
