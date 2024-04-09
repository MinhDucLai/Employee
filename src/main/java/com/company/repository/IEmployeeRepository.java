package com.company.repository;

import com.company.form.EmployeeFilterForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.company.entity.Employee;

import java.util.List;

@Mapper
public interface IEmployeeRepository{

	List<Employee> getAllEmployees(EmployeeFilterForm form);

	List<String> getAllEmails();
	Employee getEmployeeById(Integer id);

	Integer countAllEmployees(EmployeeFilterForm form);
	Boolean existsById(Integer id);
	Boolean existsByEmail(String email);

	Boolean existsByPhoneNumber(String phoneNumber);

	Boolean existsByEmployeeNumber(Integer employeeNumber);

	void createEmployee(Employee employee);

	void updateEmployee(Employee employee);

	void deleteEmployee(Employee employee);
}
