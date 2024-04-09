package com.company.validation;

import com.company.service.IEmployeeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeNumberNotExistsValidator implements ConstraintValidator<EmployeeNumberNotExists, Integer> {

	@Autowired
	private IEmployeeService service;

	@Override
	public boolean isValid(Integer employeeNumber, ConstraintValidatorContext context) {

		if (employeeNumber == null || employeeNumber <= 0) {
			return true;
		}

		return !service.isEmployeeExistsByEmployeeNumber(employeeNumber);
	}
}