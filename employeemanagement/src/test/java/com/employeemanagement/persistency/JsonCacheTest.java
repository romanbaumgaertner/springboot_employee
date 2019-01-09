package com.employeemanagement.persistency;

import org.junit.Test;

import com.employeemanagement.models.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;

public class JsonCacheTest {
	
	@After public  void deleteEntries() {
		JsonCache cache = JsonCache.getInstance();
		cache.deleteAll();
	}
	
	@Test
	public void testGetInstance() {
		JsonCache cache1 = JsonCache.getInstance();
		
		Assert.assertNotNull("object is null", cache1);
		
		// get another instance
		JsonCache cache2 = JsonCache.getInstance();
		Assert.assertEquals(cache2, cache1);	
		
	}
	
	@Test
	public void testAdd() throws JSONException {
		JsonCache cache = JsonCache.getInstance();
		
		JSONObject obj = new JSONObject();
		obj.put(JsonCache.DEPARTMENT, "Dep1");
		obj.put(JsonCache.EMAIL, "toni@test.com");
		obj.put(JsonCache.FIRSTNAME, "toni");
		obj.put(JsonCache.LASTNAME, "test");
		obj.put(JsonCache.ID, 1);
		
		cache.addEntity(obj);
		
		Assert.assertEquals(1, cache.size());
	}
	
	@Test
	public void testGetResource() throws JSONException {
		JsonCache cache = JsonCache.getInstance();
		
		JSONObject obj = new JSONObject();
		obj.put(JsonCache.DEPARTMENT, "Dep1");
		obj.put(JsonCache.EMAIL, "toni@test.com");
		obj.put(JsonCache.FIRSTNAME, "toni");
		obj.put(JsonCache.LASTNAME, "test");
		obj.put(JsonCache.ID, 1);
		
		cache.addEntity(obj);
		JSONObject obj2 = cache.getResource(1);
		
		Assert.assertEquals(1, cache.size());
		Assert.assertEquals("not equal", (String)obj.getString(JsonCache.DEPARTMENT), (String)obj2.getString(JsonCache.DEPARTMENT));
		Assert.assertEquals("not equal", (String)obj.getString(JsonCache.EMAIL), (String)obj2.getString(JsonCache.EMAIL));
		Assert.assertEquals("not equal", (String)obj.getString(JsonCache.LASTNAME), (String)obj2.getString(JsonCache.LASTNAME));
		Assert.assertEquals("not equal", (String)obj.getString(JsonCache.FIRSTNAME), (String)obj2.getString(JsonCache.FIRSTNAME));
	}
	
	@Test
	public void testGetAll() throws JSONException {
		JsonCache cache = JsonCache.getInstance();
		
		JSONObject obj1 = new JSONObject();
		obj1.put(JsonCache.DEPARTMENT, "Dep1");
		obj1.put(JsonCache.EMAIL, "toni@test.com");
		obj1.put(JsonCache.FIRSTNAME, "toni");
		obj1.put(JsonCache.LASTNAME, "test");
		obj1.put(JsonCache.ID, 1);
		
		JSONObject obj2 = new JSONObject();
		obj2.put(JsonCache.DEPARTMENT, "Dep2");
		obj2.put(JsonCache.EMAIL, "toni@test.com");
		obj2.put(JsonCache.FIRSTNAME, "toni");
		obj2.put(JsonCache.LASTNAME, "test");
		obj2.put(JsonCache.ID, 2);
		
		cache.addEntity(obj1);
		cache.addEntity(obj2);
		JSONArray arr = cache.getAll();
		
		Assert.assertEquals(cache.size(), arr.length());
	}
	
	
	
	@Test
	public void testUpdate() throws Exception {
		
		JsonCache cache = JsonCache.getInstance();
		
		JSONObject obj1 = new JSONObject();
		obj1.put(JsonCache.DEPARTMENT, "Dep1");
		obj1.put(JsonCache.EMAIL, "toni@test.com");
		obj1.put(JsonCache.FIRSTNAME, "toni");
		obj1.put(JsonCache.LASTNAME, "test");
		obj1.put(JsonCache.ID, 1);
		
		JSONObject obj2 = new JSONObject();
		obj2.put(JsonCache.DEPARTMENT, "Dep2");
		obj2.put(JsonCache.EMAIL, "toni@test.com");
		obj2.put(JsonCache.FIRSTNAME, "toni");
		obj2.put(JsonCache.LASTNAME, "test");
		obj2.put(JsonCache.ID, 1);
		
		cache.addEntity(obj1);
		cache.modifyResource(1, obj2);
		JSONObject obj3 = cache.getResource(1);
		Assert.assertEquals("not equal", (String)obj2.getString(JsonCache.DEPARTMENT), (String)obj3.getString(JsonCache.DEPARTMENT));
	}
	
	
	@Test
	public void testRemove() throws Exception {
			JsonCache cache = JsonCache.getInstance();
			
			JSONObject obj = new JSONObject();
			obj.put(JsonCache.DEPARTMENT, "Dep1");
			obj.put(JsonCache.EMAIL, "toni@test.com");
			obj.put(JsonCache.FIRSTNAME, "toni");
			obj.put(JsonCache.LASTNAME, "test");
			obj.put(JsonCache.ID, 1);
			
			// we add entity to cache
			cache.addEntity(obj);
			Assert.assertEquals(1, cache.size());
			
			// remove id
			cache.deleteResource(1);
			
			Assert.assertEquals(0, cache.size());
		}
		
		
	}
