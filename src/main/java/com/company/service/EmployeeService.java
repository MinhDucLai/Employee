package com.company.service;


import com.company.dto.CommonListDTO;
import com.company.form.EmployeeRegisterForm;
import com.company.form.EmployeeUpdateForm;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.company.entity.Employee;
import com.company.form.EmployeeFilterForm;
import com.company.repository.IEmployeeRepository;

import jakarta.transaction.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

	@Autowired
	private IEmployeeRepository employeeRepository;

	@Autowired
	private ExcelService excelService;
	@Override
	public CommonListDTO<Employee> getAllEmployees(EmployeeFilterForm form) {
		List<Employee> employees = employeeRepository.getAllEmployees(form);
		if (employees.isEmpty()){
			return  new CommonListDTO<Employee>(
					employees,
					0
			);
		}
		return new CommonListDTO<Employee>(
				employees,
				employeeRepository.countAllEmployees(form)
		);
	}

	@SuppressWarnings("static-access")
	@Transactional
	@Override
	public void createEmployee(EmployeeRegisterForm form) throws Exception {

		Employee employee = new Employee().builder()
				.employee_number(form.getEmployeeNumber())
				.name(form.getName())
				.phone_number(form.getPhoneNumber())
				.email(form.getEmail())
				.position(form.getPosition())
				.build();
		employeeRepository.createEmployee(employee);

	}

	@Override
	public void updateEmployee(Integer id, EmployeeUpdateForm form) {
		Employee employee = employeeRepository.getEmployeeById(id);
		employee.setEmployee_number(form.getEmployeeNumber());
		employee.setName(form.getName());
		employee.setPhone_number(form.getPhoneNumber());
		employee.setPosition(form.getPosition());
		employee.setEmail(form.getEmail());
		employeeRepository.updateEmployee(employee);
	}

	@Override
	public Employee getEmployeeById(Integer id) {
		return employeeRepository.getEmployeeById(id);
	}

	@Override
	public void deleteEmployee(Integer id) {
		Employee employee = employeeRepository.getEmployeeById(id);
		employeeRepository.deleteEmployee(employee);
	}

	@Override
	public Boolean isEmployeeExistsById(Integer id) {
		return employeeRepository.existsById(id);
	}
//
	@Override
	public Boolean isEmployeeExistsByEmail(String email) {
		return employeeRepository.existsByEmail(email);
	}
//
	@Override
	public Boolean isEmployeeExistsByPhoneNumber(String phoneNumber) {
		return employeeRepository.existsByPhoneNumber(phoneNumber);
	}
//
	@Override
	public Boolean isEmployeeExistsByEmployeeNumber(Integer employeeNumber) {
		return employeeRepository.existsByEmployeeNumber(employeeNumber);
	}

	@Override
	public void exportFileExcel(String typeFile, String excelFilePath) throws IOException {
		excelService.writeExcel(excelFilePath);
	}

	@Override
	public ResponseEntity<String> exportToCSV(String filePath) {
		List<Employee> employees = employeeRepository.getAllEmployees(new EmployeeFilterForm());

		try {
			exportFileToCSV(employees, filePath);
			return ResponseEntity.ok("CSV exported successfully to: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error exporting CSV.");
		}
	}
	private void exportFileToCSV(List<Employee> employees, String filePath) throws IOException {
		try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
			// Writing headers
			String[] headers = {"Employee Number", "Name","Phone Number","Email","Position"};
			writer.writeNext(headers);

			// Writing data
			for (Employee employee : employees) {
				String[] data = {String.valueOf(employee.getEmployee_number()),employee.getName(),employee.getPhone_number(),employee.getEmail(),employee.getPosition()};
				writer.writeNext(data);
			}
		}
	}
}
