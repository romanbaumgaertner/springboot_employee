package com.employeemanagement.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


// Note - was having problems getting the repository recognized by Spring
//        needed to add additional annotations ... weird
@SpringBootApplication
@ComponentScan(basePackages = {"com.employeemanagement.controller","com.employeemanagement.app"})
@EnableJpaRepositories("com.employeemanagement.repositories")
@EntityScan("com.employeemanagement.models")
public class EmployeeManagementApplication {
	
	private static final Logger logger = LogManager
			.getLogger(EmployeeManagementApplication.class);

	public static void main(String[] args) {
		
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

}

