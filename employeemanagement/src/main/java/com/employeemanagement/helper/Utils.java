package com.employeemanagement.helper;

import org.json.JSONObject;

import com.employeemanagement.models.Employee;
import com.employeemanagement.persistency.JsonCache;

public class Utils {
	
	// Helper
	public static JSONObject convertToJson(Employee e) throws Exception {
		JSONObject obj = new JSONObject();
		if( e == null)
			throw new Exception("invalid object");
		
		obj.put(JsonCache.DEPARTMENT, e.getDepartment());
		obj.put(JsonCache.EMAIL, e.getEmail());
		obj.put(JsonCache.FIRSTNAME, e.getFirstName() );
		obj.put(JsonCache.LASTNAME, e.getLastName());
		obj.put(JsonCache.ID, e.getId());
		
		return obj;
	}

}
