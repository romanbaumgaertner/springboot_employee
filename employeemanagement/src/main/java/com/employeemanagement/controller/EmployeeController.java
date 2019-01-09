package com.employeemanagement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.employeemanagement.app.EmployeeManagementApplication;
import com.employeemanagement.models.Employee;

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
 */

@RestController
@RequestMapping("/management")
public class EmployeeController {
	
	private static final Logger logger = LogManager
			.getLogger(EmployeeManagementApplication.class);
	
	private static long id;  // resourceId, normally is auto-generated when Entity class
	                         // in folder model is correctly hooked up
	
	// get employee with id <id>
	@GetMapping("/employee/{id}")
	public Employee getEmployee(@PathVariable long id){
		
		//TODO: get resource with id from JSON file
		
		Employee employee = new Employee();
		employee.setId(id);
		employee.setFirstName("d");
		employee.setLastName("d");
		employee.setEmail("d");
		employee.setDepartment("d");
		
		return employee;
	}
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getEmployees(){
		
		List<Employee> list = new ArrayList<Employee>();
		
		Employee employee = new Employee();
		employee.setId(id);
		employee.setFirstName("d");
		employee.setLastName("d");
		employee.setEmail("d");
		employee.setDepartment("d");
		
		list.add(employee);
		
		return list;
	}	

	// create new resource
	@PostMapping("/employee")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee employee(
			@Valid @RequestBody Employee e){
		
		id++;

		Employee employee = new Employee();
		employee.setId(id);
		employee.setFirstName(e.getFirstName());
		employee.setLastName(e.getLastName());
		employee.setEmail(e.getEmail());
		employee.setDepartment(e.getDepartment());

		return employee;
	}
	
	
	// update employee with id <id>
	@PutMapping("/employee/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Employee updateEmployee(
			@PathVariable long id,
			@Valid @RequestBody Employee e) {

		Employee employee = new Employee();

		employee.setFirstName(e.getFirstName() );
		employee.setLastName(e.getLastName() );
		employee.setEmail(e.getEmail() );
		employee.setDepartment(e.getDepartment());

		return employee;
	}
	
	// delete resource with id <id>
	@DeleteMapping("/employee/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String delete(@PathVariable("id") Long id) {
		return "{\"response\":\"entry " +id +" deleted\"}";
	}
	
	// delete all entries
	@DeleteMapping("/employees")
	public String deleteAll() {
		return "{\"response\":\"all entries deleted\"}";
	}
	
}
