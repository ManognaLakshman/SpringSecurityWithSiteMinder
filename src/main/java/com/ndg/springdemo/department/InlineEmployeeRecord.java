package com.ndg.springdemo.department;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.ndg.springdemo.model.Employee;

@Projection(name = "inlineEmployeeRecord", types = { Employee.class }) //FOR EMPLOYEE
public interface InlineEmployeeRecord {
	int getid();
	String getname();
	String getSkill();
	float getSalary();
	int getGrade();
	String getCity();
	String getCountry();
	Date getDoj();
	String getDesg();
}
