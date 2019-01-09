package com.employeemanagement.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.models.*;

@Repository     
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	//if wanted I can add here custom queries
}
