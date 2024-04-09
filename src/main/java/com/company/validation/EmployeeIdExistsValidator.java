package com.company.validation;

import com.company.service.IEmployeeService;
import com.company.validation.EmployeeIdExists;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.service.EmployeeService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeIdExistsValidator implements ConstraintValidator<EmployeeIdExists, Integer> {

	@Autowired
	private IEmployeeService service;

	@Override
	public boolean isValid(Integer id, ConstraintValidatorContext context) {

		if (id == null || id <= 0) {
			return false;
		}

		return service.isEmployeeExistsById(id);
	}
}