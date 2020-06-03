package com.incture.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.incture.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
	/*
	 *  In CRUDRepository basic CRUD operation methods are predifined.
	 *  If you want to write custom query write like below. 
	 *  if we use findBy and after the property name(which we defined in Entity class) it automatically fetch the data 
	 *  based on that name (it works like where condition).
	 */
	
	public List<Employee> findByEmpName(String empName);
}
