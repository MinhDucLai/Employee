package com.company.validation;

import com.company.service.IEmployeeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class EmployeeEmailNotExistsValidator implements ConstraintValidator<EmployeeEmailNotExists, String> {

	@Autowired
	private IEmployeeService service;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {

		if (StringUtils.isEmpty(email)) {
			return true;
		}

		return !service.isEmployeeExistsByEmail(email);
	}

}