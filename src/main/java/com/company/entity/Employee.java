package com.company.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@SuperBuilder

public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonProperty(value = "employeeNumber")
	@Column(name = "employee_number",unique = true)
	private Integer employee_number;

	@Column(name = "name", nullable = false)
	private String name;

	@JsonProperty(value = "phoneNumber")
	@Column(name = "phone_number", nullable = false, unique = true)
	private String phone_number;

	@Column(name = "position")
	private String position;

	@Column(name = "email", unique = true)
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployee_number() {
		return employee_number;
	}

	public void setEmployee_number(Integer employee_number) {
		this.employee_number = employee_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
