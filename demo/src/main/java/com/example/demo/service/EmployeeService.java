package com.example.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.InnerDtoForConversation;
import com.example.demo.config.InnerDtoForReplies;
import com.example.demo.config.OuterDto;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepo;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	public Iterable<Employee> saveEmployee(List<Employee> employees) {
		return employeeRepo.saveAll(employees);
	}

	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<>();
		employeeRepo.findAll().forEach(employees::add);
		return employees;
	}

	public OuterDto getEmployees() {
		OuterDto outerDto = null;
		List<Employee> memory;
		StringBuilder content = new StringBuilder();
		StringBuilder builder = null;
		try {
			memory = getAll();
			outerDto = new OuterDto();
			InnerDtoForConversation innerDtoForConversation = new InnerDtoForConversation();
			InnerDtoForReplies innerDtoForReplies = new InnerDtoForReplies();
			innerDtoForReplies.setType("text");
			if (memory != null) {

				for (Employee employee : memory) {
					builder = new StringBuilder();
					builder.append(
							employee.getId() + " " + employee.getEmpName() + " " + employee.getContact() + ". \n\n");

					content.append(builder);
				}
			}
			if (builder == null) {
				builder = new StringBuilder();
				builder.append("No leave approvals in your queue.");
				content.append(builder);
			}

			innerDtoForReplies.setContent(content.toString());
			innerDtoForConversation.setLanguage("en");
			innerDtoForConversation.setMemory(memory);

			innerDtoForConversation.setMerge_memory(true);

			List<InnerDtoForReplies> list = new ArrayList<InnerDtoForReplies>();
			list.add(innerDtoForReplies);
			outerDto.setReplies(list);
			outerDto.setConversation(innerDtoForConversation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outerDto;
	}

	public List<Employee> readExcel(String fileLocation) throws InvalidFormatException {
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee = null;
		File file1 = new File(fileLocation);
		DataFormatter dataFormat = new DataFormatter();
		try {
			XSSFWorkbook workbook = null;
			workbook = new XSSFWorkbook(file1);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			Row row0 = sheet.getRow(0);
			int cellCount = row0.getPhysicalNumberOfCells();
			for (int r = 1; r < rowCount; r++) {
				employee = new Employee();
				Row row = sheet.getRow(r);
				for (int c = 0; c < cellCount; c++) {
					switch (dataFormat.formatCellValue(row0.getCell(c))) {
					case "EMP_NAME": {
						employee.setEmpName(dataFormat.formatCellValue(row.getCell(c)));
						break;
					}
					case "CONTACT_NO": {
						employee.setContact(dataFormat.formatCellValue(row.getCell(c)));
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
