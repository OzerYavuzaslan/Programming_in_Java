package com.udemy.entityRelationships1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS_TABLE")
public class Address
{
	@Id
	@GeneratedValue
	private int address_id;
	
	@Column(name="address_name")
	private String address_name;
	
	@Column(name="zip_come")
	private int zip_code;
	
	@OneToOne
	@JoinColumn(name="employee_id")
	private Employee emp;

	public Address()
	{
		
	}

	public String getAddress_name() {
		return address_name;
	}

	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}

	public int getZip_code() {
		return zip_code;
	}

	public void setZip_code(int zip_code) {
		this.zip_code = zip_code;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}
}
