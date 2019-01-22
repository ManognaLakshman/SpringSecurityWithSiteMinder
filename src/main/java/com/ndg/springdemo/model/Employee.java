package com.ndg.springdemo.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



@Entity
public class Employee {
	public static final int DEFAULT_ID=-1;

	   @Id
	   @GeneratedValue
	   private int id = DEFAULT_ID;
	   private String name;
	   private String skill,desg;
	   private float salary;
	   private int grade;
	   private String city,country;
	   
	   @Temporal(TemporalType.DATE)
	   private Date doj;	  

	   
	   @ManyToOne
	   @JoinColumn(name="deptid")

	   private Department dept;
	   

	   
		public float getSalary() {
			return salary;
		}
		public Department getDept() {
			return dept;
		}
		public void setDept(Department deptid) {
			this.dept = deptid;
		}
		public void setSalary(float salary) {
			this.salary = salary;
		}
		public int getGrade() {
			return grade;
		}
		public void setGrade(int grade) {
			this.grade = grade;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSkill() {
			return skill;
		}
		public void setSkill(String skill) {
			this.skill = skill;
		}
		public String getDesg() {
			return desg;
		}
		public void setDesg(String desg) {
			this.desg = desg;
		}

		public Date getDoj() {
			return doj;
		}
		public void setDoj(Date doj) {
			this.doj = doj;
		}
	

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", skill=" + skill + ", desg=" + desg + ", Dept=" 
				+ ", doj=" + doj + "]";
	}

	  
}