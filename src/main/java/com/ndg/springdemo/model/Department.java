package com.ndg.springdemo.model;

import java.util.HashSet;
//import lombok.Data;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//@Data
@Entity
public class Department {

	public static final int DEFAULT_DEPT_ID=-1;
	 @Id
	 @GeneratedValue 
	 private int deptid=DEFAULT_DEPT_ID;
	 private String deptname;
	 
	 @OneToOne
	 @JoinColumn(name="id",nullable=true)
//	 @JsonManagedReference
//	 @JsonIgnore
	 private Employee depthead;
	 
	 
	public Employee getDepthead() {
		return depthead;
	}


	public void setDepthead(Employee depthead) {
		this.depthead = depthead;
	}


	public int getDeptid() {
		return deptid;
	}


	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
}
