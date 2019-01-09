package com.employeemanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.employeemanagement.app.EmployeeManagementApplication;
import com.employeemanagement.models.Employee;
import com.employeemanagement.persistency.JsonCache;
import com.employeemanagement.repositories.EmployeeRepository;

/***
 * Controller supports the following CRUD APIs
 * 
 * + get employee by id
 * + get all employees
 * + create employee entity
 * + update employee entity
 * + delete employee entity by id
 * + delete all employees
 * 
 * The Controller supports two version of APIs
 * 
 * + v1: using local cache
 * + v2: using persistent data store (MySQL)
 * 
 *
 */

@RestController
@RequestMapping("/management")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	// Note -  did not add any additional logging
	private static final Logger logger = LogManager
			.getLogger(EmployeeManagementApplication.class);
	
	// "id" is only relevant for API v1
	private static long id;  // resourceId, normally is auto-generated when Entity class
	                         // in folder model is correctly hooked up
							 // for demo point of view, dirty hack

	
	// get employee with id <id>
	@GetMapping("v1/employee/{id}")
	public String getEmployee(@PathVariable long id){
		
		JsonCache cache = JsonCache.getInstance();
		JSONObject obj;
		try {
			obj = cache.getResource(id);
		} catch (Exception e) {
			 throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "resource not found", e);
		}
		
		return obj.toString();
	}
	
	// Note:
	// Example how to support versioning
	//   the API below could contain a different logic
	//  get employee with id <id>
	@GetMapping("v2/employee/{id}")
	public ResponseEntity<Employee> getEmployeeV2(@PathVariable long id){
		
		Optional<Employee> employee = employeeRepository.findById(id);
		
		Employee employeeResult = null;
		if(employee.isPresent()) {
			employeeResult = employee.get();
			
		}else {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "resource id not found");
		}
		
		return (ResponseEntity<Employee>) ResponseEntity.ok().body(employeeResult);

	}
	
	//get all employees
	@GetMapping("v1/employees")
	public String getEmployees(){
		
		JsonCache cache = JsonCache.getInstance();
		JSONArray all = cache.getAll();
		
		return all.toString();
	}	
	
	//get all employees
	@GetMapping("v2/employees")
	public List<Employee> getEmployees2(){
		
		return employeeRepository.findAll();

	}	

	// create new resource
	@PostMapping("v1/employee")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee(
			@Valid @RequestBody Employee e) throws Exception{

		id++; 		// for quick and dirty I am setting the id here
					// for better abstraction id generation should happen in the persistency layer

		Employee employee = new Employee();
		
		employee.setId(id);
		employee.setFirstName(e.getFirstName());
		employee.setLastName(e.getLastName());
		employee.setEmail(e.getEmail());
		employee.setDepartment(e.getDepartment());
		
		JsonCache cache = JsonCache.getInstance();
		
		JSONObject obj;
		try {
			obj = convertToJson(employee);
			cache.addEntity(obj);
		} catch (Exception e1) {
			 throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "something went wrong", e1);
		}
		
		return employee;
	}
	
	// create new resource
	@PostMapping("v2/employee")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee2(
			@Valid @RequestBody Employee e) throws Exception{
		
		return employeeRepository.save(e);
		
	}
	
	
	// update employee with id <id>
	@PutMapping("v1/employee/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String updateEmployee(
			@PathVariable long id,
			@Valid @RequestBody Employee e) {
		
		JsonCache cache = JsonCache.getInstance();
		JSONObject object;
		try {
			object = convertToJson(e);
			cache.modifyResource(id, object);
			object = cache.getResource(id);

		} catch (Exception e1) {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "update request problem", e1);
		}
		
		return object.toString();
	}
	
	@PutMapping("v2/employee/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Employee updateEmployee2(
			@PathVariable long id,
			@Valid @RequestBody Employee e) {
		
		Optional<Employee> employee = employeeRepository.findById(id);
		
		Employee employeeResult = null;
		if(employee.isPresent()) {
			employeeResult = employee.get();
			employeeResult.setDepartment(e.getDepartment());
			employeeResult.setEmail(e.getEmail());
			employeeResult.setFirstName(e.getFirstName());
			employeeResult.setLastName(e.getLastName());
			
		}else {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "resource id not found");
		}
		
		
		return employeeRepository.save(employeeResult);
	}
	
	// delete resource with id <id>
	@DeleteMapping("v1/employee/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String delete(@PathVariable("id") Long id) {
		JsonCache cache = JsonCache.getInstance();
		try {
			cache.deleteResource(id);
		} catch (Exception e1) {
			 throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "delete request problem", e1);
		}
		
		return "{\"response\":\"entry " +id +" deleted\"}";
	}
	
	@DeleteMapping("v2/employee/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Employee> delete2(@PathVariable("id") Long id) {

		Optional<Employee> employee = employeeRepository.findById(id);
		
		Employee employeeResult = null;
		if(employee.isPresent()) {
			employeeResult = employee.get();
			employeeRepository.delete(employeeResult);
			
		}
		
		return ResponseEntity.ok(employeeResult);
	}
	
	// delete all entries
	@DeleteMapping("v1/employees")
	public String deleteAll() {
		
		JsonCache cache = JsonCache.getInstance();
		cache.deleteAll();
		
		return "{\"response\":\"all entries deleted\"}";
	}
	
	// delete all entries
	@DeleteMapping("v2/employees")
	public ResponseEntity<?> deleteAll2() {
		
		employeeRepository.deleteAll();
		
		return new ResponseEntity<>("{\"response\":\"deleted all\"}", HttpStatus.OK);
		
	}
	
	// Note - helper code should be in Utils package
	// Helper
	public JSONObject convertToJson(Employee e) throws Exception {
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
