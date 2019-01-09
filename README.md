Employee REST using SpringBoot
==============================

# Objective

The following project implements a simple SpringBoot REST controller which
exposes CRUD operations on an Employee entity.

# Spring Boot Framework

As web framework I used the SpringBoot framework which comes with support for embedded servers, REST controller and data persistency.


# How to run the project?

The application has the following dependencies 

- Java 1.8 
- uses the Gradle build framework. 
- uses MySQL or MariaDB 


To build the application do the following



1. Configuration of Mysql in application.properties

	- spring.datasource.url=jdbc:mysql://<the location of your DB>:3306/employee?useSSL=false
	- spring.datasource.username=<your username>
	- spring.datasource.password=<your password>

Also you need to create the database with

	> create DATABASE employee;

2. cd into the root project folder
3. execute ./gradlew build     // gets all the dependencies from the web
4. run the application with 

   java -jar build/libs/employeemanagement-0.0.1-EMPLOYEEMANAGEMENT.jar


After successfully starting the application, you should see the newly created table in the MySQL shell with

	- >use employee;
        - >show tables;



# Supported Rest APIs

The application supports 2 versions of REST APIs

- v1: local cache
- v2: uses MySQL as datastore

The following REST APIs are supported

GET requests

http://127.0.0.1:8080/management/v2/employee/<id>
- returns an employee with id <id>


http://127.0.0.1:8080/management/v2/employees
- returns all employees 


POST request
http://127.0.0.1:8080/management/v2/employee 

body
{
  "firstName": "Toni",
  "lastName": "Bear",
  "email": "tb@yahoo.com",
  "department": "dep1"
}

PUT request to update an existing resource

http://127.0.0.1:8080/management/v2/employee/1

body
{
  "firstName": "Tod",
  "lastName": "Bear",
  "email": "tb@yahoo.com",
  "department": "dep1"
}

DELETE requests to delete a single resource and all resources

http://127.0.0.1:8080/management/v2/employee/2

http://127.0.0.1:8080/management/v2/employees

Note: Please, make sure that you use application/json as contents type and also set Accept to application/json when you send the REST request.


# What is missing?

Unfortunately I was having problems with deploying the app on AWS over Elasticbeantalk (database connection problems) otherwise I would have loved to add

- HTTPS support
- adding swagger for API documention
- complete unit tests for the controller


# AWS deployment

Create an Elasticbeantalk project using command line interface. Elasticbeantalk offers the following advantages

- easy setup of EC2 instance
- easy setup of RDS instance (using MySql)
- Nginx loadbalancer

To configure the port correctly only 

	 eb setenv SERVER_PORT=5000


is needed to ensure that the loadbalance gets the REST request. Request are than distributed to the running app instances. 


 



