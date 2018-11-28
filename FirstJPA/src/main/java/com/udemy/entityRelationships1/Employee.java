package com.udemy.entityRelationships1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE_TABLE")
public class Employee
{
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="employee_name")
	private String emp_name;
	
	@OneToOne(mappedBy="emp")
	private Address address;
	
	public Employee()
	{
		
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
