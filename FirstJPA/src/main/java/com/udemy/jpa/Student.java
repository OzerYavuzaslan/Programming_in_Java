package com.udemy.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Student")
@NamedQueries({ @NamedQuery(name="student.getAll", query="SELECT s FROM Student s"),
				@NamedQuery(name="student.getPersonById", query="SELECT s FROM Student s WHERE s.id =:id")})
public class Student
{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="age")
	private int studentAge;
	
	@Column(name="name")
	private String studentName;

	@Transient
	private String address;
	
	public Student()
	{
		
	}
	
	public Student(int studentAge, String studentName, String address)
	{
		this.studentAge = studentAge;
		this.studentName = studentName;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(int studentAge) {
		this.studentAge = studentAge;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString()
	{
		return this.studentName + " - " + this.studentAge;
	}
}
