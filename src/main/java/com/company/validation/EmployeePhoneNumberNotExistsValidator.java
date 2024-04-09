package com.company.validation;

import com.company.service.IEmployeeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class EmployeePhoneNumberNotExistsValidator implements ConstraintValidator<EmployeePhoneNumberNotExists, String> {

	@Autowired
	private IEmployeeService service;

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {

		if (StringUtils.isEmpty(phoneNumber)) {
			return true;
		}

		return !service.isEmployeeExistsByPhoneNumber(phoneNumber);
	}

}