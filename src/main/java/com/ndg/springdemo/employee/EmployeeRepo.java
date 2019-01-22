package com.ndg.springdemo.employee;

//import org.springframework.data.repository.query.Param;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
//import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.ndg.springdemo.common.ApplicationRepository;
import com.ndg.springdemo.model.Employee;


interface EmployeeRepo extends ApplicationRepository<Employee, Integer> {

}
