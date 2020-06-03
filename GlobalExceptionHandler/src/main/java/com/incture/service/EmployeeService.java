package com.incture.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.entity.Employee;
import com.incture.exception.GenericConditionalException;
import com.incture.repository.EmployeeRepository;

@Service("EmployeeService")
public class EmployeeService {
	@Autowired
	EmployeeRepository repository;

	public List<Employee> getAllEmployee() {
		List<Employee> empList = new ArrayList<Employee>();
		repository.findAll().forEach(empList::add);
		if (empList.size() <= 0) {
			throw new GenericConditionalException("No Employee Found");
		}
		return empList;
	}

	public Employee saveEmployee(Employee emp) {
		Employee employee = null;
		employee = repository.save(emp);
		if (employee == null) {
			throw new GenericConditionalException("Employee Not saved Successfully");
		}
		return employee;
	}

}
