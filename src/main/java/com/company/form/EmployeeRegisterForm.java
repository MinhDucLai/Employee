package com.company.form;

import com.company.validation.EmployeeEmailNotExists;
import com.company.validation.EmployeeNumberNotExists;
import com.company.validation.EmployeePhoneNumberNotExists;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeRegisterForm {
	
	@Positive
	@NotNull(message = "Employee Number is require!")
	@EmployeeNumberNotExists
	private Integer employeeNumber;
	
	@NotBlank(message = "Name is require!")
	@Length(max = 50)
	private String name;
	
	@NotBlank(message = "Phone number is require!")
	@EmployeePhoneNumberNotExists
	@Pattern(regexp="[0-9]+", message="Phone number must contain only digits")
	@Size(min=10, max=10, message="Phone number must be 10 digits")
	private String phoneNumber;
	
	private String position;

	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@EmployeeEmailNotExists
	private String email;
}
