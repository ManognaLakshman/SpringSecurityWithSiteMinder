package com.ndg.springdemo.employee;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import com.ndg.springdemo.department.DepartmentRecord;
import com.ndg.springdemo.model.Employee;

@Projection(name = "employeeRecord", types = { Employee.class }) //FOR EMPLOYEE
public interface EmployeeRecord{
	int getid();
	String getname();

	DepartmentRecord getdept();
	//
	
	String getSkill();
	float getSalary();
	int getGrade();
	String getCity();
	String getCountry();
	Date getDoj();
	String getdesg();
}
