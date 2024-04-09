package com.company.controller;

import com.company.dto.CommonListDTO;
import com.company.service.IEmailService;
import com.company.validation.EmployeeIdExists;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.company.entity.Employee;
import com.company.form.EmployeeFilterForm;
import com.company.form.EmployeeRegisterForm;
import com.company.form.EmployeeUpdateForm;
import com.company.service.IEmployeeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@Validated
public class EmployeeController {
	@Autowired
	private IEmployeeService employeeService;

    @Autowired
    private IEmailService emailService;

	@GetMapping
	public CommonListDTO<Employee> getAllEmployees(@Valid EmployeeFilterForm filterForm) {
		return employeeService.getAllEmployees(filterForm);
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable(name = "id")  @EmployeeIdExists Integer  id) {
		return employeeService.getEmployeeById(id);
	}

	@PostMapping
	public String createEmployee(@RequestBody @Valid EmployeeRegisterForm form) throws Exception {
		employeeService.createEmployee(form);
		return "create successfully";
	}

	@PutMapping("/{id}")
	public String updateEmployee(
			@PathVariable(name = "id") Integer id,
			@RequestBody @Valid EmployeeUpdateForm form) {
		employeeService.updateEmployee(id,form);
		return "update successfully";
	}

	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable(name = "id") @EmployeeIdExists Integer id) throws Exception {
		employeeService.deleteEmployee(id);
		return "Delete successfully";

	}
	@GetMapping("/export")
	public String exportFileExcel(@RequestParam String typeFile) throws IOException {
		final String excelFilePath = "D:\\employees." + typeFile;
		employeeService.exportFileExcel(typeFile,excelFilePath);
		return "export file successfully in "+ excelFilePath;
	}

	@GetMapping("/export/csv")
	public String exportFileCSV( ) throws IOException {
		final String filePath = "D://employees.csv";
		employeeService.exportToCSV(filePath);
		return "export file successfully in "+ filePath;
	}

    @GetMapping("/send-email")
    public String sendEmail() throws MessagingException {
        // Code to send email using EmailService
        emailService.sendEmail("duc0926863584@gmail.com", "subject", "<h1>Demo Content</h1><p>This is a demo email content.</p>" );
        return "Email sent successfully!";
    }


    @GetMapping("/employeeNumber/exists")
	public Boolean existByEmployeeNumber( Integer employeeNumber){
		return employeeService.isEmployeeExistsByEmployeeNumber(employeeNumber);
	}

	@GetMapping("/email/exists")
	public Boolean existByEmail( String email){
		return employeeService.isEmployeeExistsByEmail(email);
	}

	@GetMapping("/phoneNumber/exists")
	public Boolean existByEmployeeNumber( String phoneNumber){
		return employeeService.isEmployeeExistsByPhoneNumber(phoneNumber);
	}
}
