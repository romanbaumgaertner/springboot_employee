package com.employeemanagement.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages ="com.employeemanagement.controller")
public class EmployeeManagementApplication {
	
	private static final Logger logger = LogManager
			.getLogger(EmployeeManagementApplication.class);

	public static void main(String[] args) {
		
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

}

