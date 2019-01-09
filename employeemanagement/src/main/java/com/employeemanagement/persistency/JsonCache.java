package com.employeemanagement.persistency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonCache {
	
	public static String ID = "id";
	public static String LASTNAME = "lastName";
	public static String FIRSTNAME = "firstName";
	public static String DEPARTMENT = "department";
	public static String EMAIL = "email";
	
	private static JsonCache jcache = null;
	private JSONArray jArray;
	
	private JsonCache() {
		jArray = new JSONArray();
	}
	
	public static JsonCache getInstance(){
		if( jcache == null) {
			jcache = new JsonCache();
		}
		
		return jcache;
	}
	
	public JSONObject getResource(long id) {
		
		for( int i = 0; i < jArray.length(); i++) {
			try {
				JSONObject object = jArray.getJSONObject(i);
				
				if(object.getLong(ID) ==id) {
					return object;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		return null;
	}
	
	public void addEntity(JSONObject object) {
		jArray.put(object);	
	}
	
	public JSONArray getAll() {
		return jArray;
	}
	
	public long size() {
		return jArray.length();
	}
	
	public void modifyResource(long id, JSONObject resource) throws Exception {
		
		boolean isFound = false;
		for( int i = 0; i < jArray.length(); i++) {
			try {
				JSONObject object = jArray.getJSONObject(i);
				
				// when resource found in cache, remove 
				if(object.getLong(ID) ==id) {
					object.put(FIRSTNAME, resource.get(FIRSTNAME));
					object.put(LASTNAME, resource.get(LASTNAME));
					object.put(DEPARTMENT, resource.get(DEPARTMENT));
					object.put(EMAIL, resource.get(EMAIL));
					isFound = true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		if(isFound == false)
			throw new Exception("resource id not found");
	}
	
	public void deleteResource(long id) throws Exception {
		
		boolean isFound = false;
		for( int i = 0; i < jArray.length(); i++) {
			try {
				JSONObject object = jArray.getJSONObject(i);
				
				// when resource found in cache, remove 
				if(object.getLong(ID) ==id) {
					jArray.remove(i);
					isFound = true;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		if(isFound == false)
			throw new Exception("resource id not found");
	}
	
	public void deleteAll() {
		jArray = new JSONArray();
	}

}
