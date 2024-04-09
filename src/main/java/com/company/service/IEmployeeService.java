package com.company.service;


import com.company.dto.CommonListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.company.entity.Employee;
import com.company.form.EmployeeFilterForm;
import com.company.form.EmployeeRegisterForm;
import com.company.form.EmployeeUpdateForm;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {

	CommonListDTO<Employee> getAllEmployees(EmployeeFilterForm form);

	Employee getEmployeeById(Integer id);

	void createEmployee( EmployeeRegisterForm form) throws Exception;

	Boolean isEmployeeExistsById(Integer id);

	Boolean isEmployeeExistsByEmail(String email);

	Boolean isEmployeeExistsByPhoneNumber(String phoneNumber);

	Boolean isEmployeeExistsByEmployeeNumber(Integer id);

	void updateEmployee(Integer id, EmployeeUpdateForm form);

	void deleteEmployee(Integer id);

   void exportFileExcel(String typeFile, String excelFilePath) throws IOException;

   ResponseEntity<String> exportToCSV(String filePath);
}
