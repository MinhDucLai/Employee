package com.company.form;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeUpdateForm {
	
	@Positive
	private Integer employeeNumber;
	
	@Length(max = 50)
	private String name;

	@Pattern(regexp="[0-9]+", message="Phone number must contain only digits")
	@Size(min=10, max=10, message="Phone number must be 10 digits")
	private String phoneNumber;
	
	private String position;

	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String email;
}
