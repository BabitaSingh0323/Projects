package com.demo.employee.serviceimpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.employee.entity.Employee;
import com.demo.employee.repository.EmployeeRepo;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	public Iterable<Employee> saveEmployee(List<Employee> employees) {
		return employeeRepo.saveAll(employees);
	}

	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<Employee>();
		employees = (List<Employee>) employeeRepo.findAll();
		return employees;
	}

	public List<Employee> readExcel(String fileLocation) throws InvalidFormatException {
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee = null;
		File file = new File(fileLocation);
		DataFormatter dataFormat = new DataFormatter();
		try {
			XSSFWorkbook workbook = null;
			workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			Row row0 = sheet.getRow(0);
			int cellCount = row0.getPhysicalNumberOfCells();
			for (int r = 1; r < rowCount; r++) {
				employee = new Employee();
				Row row = sheet.getRow(r);
				for (int i = 0; i < cellCount; i++) {
					switch (dataFormat.formatCellValue(row0.getCell(i))) {
					case "EMP_NAME": {
						employee.setEmpName(dataFormat.formatCellValue(row.getCell(i)));
						break;
					}
					case "CONTACT_NO": {
						employee.setContact(dataFormat.formatCellValue(row.getCell(i)));
						break;
					}
					case "DESIGNATION": {
						employee.setDesignation(dataFormat.formatCellValue(row.getCell(i)));
						break;
					}
					}
					employees.add(employee);
					employees = (List<Employee>) saveEmployee(employees);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employees;

	}
}
