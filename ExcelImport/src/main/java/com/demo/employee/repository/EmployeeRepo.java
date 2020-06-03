package com.demo.employee.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.employee.entity.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Integer>{

}
