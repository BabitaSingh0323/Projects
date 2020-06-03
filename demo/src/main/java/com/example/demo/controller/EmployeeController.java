package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.OuterDto;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(value = "/")
	public List<Employee> getAll(){
		System.out.println("inside controller");
		
		try {
			return employeeService.getAll();
		} catch (Exception e) {
		}
		return null;
	}
	
	@PostMapping(value = "/addEmployee")
	public void addEmployee(@RequestBody List<Employee> employee) {
		System.out.println("employee"+employee);
		employeeService.saveEmployee(employee);
	}
	
	@GetMapping("/getEmployees")
	public OuterDto getEmployees() {
		return employeeService.getEmployees();
		
	}
	@PostMapping(value = "/importExcel")
	public List<Employee> readExcel(@RequestParam("file") MultipartFile file) {
		try {
			InputStream inputStream = file.getInputStream();
			File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			System.err.println(path);
			String fileLocation = path.substring(0, path.length() - 1)
					+ file.getOriginalFilename();
			System.out.println(fileLocation);
			FileOutputStream f = new FileOutputStream(fileLocation);
			int ch = 0;
			while ((ch = inputStream.read()) != -1) {
				f.write(ch);
			}
			f.flush();
			f.close();
			return employeeService.readExcel(fileLocation);
		} catch (Exception e) {
		}
		return null;
		
	}
}
