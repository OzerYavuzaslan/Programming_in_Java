package com.udemy.entityRelationships2;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class University
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int university_id;
	
	private String university_name;
	
	@OneToMany(mappedBy="university")
	private List<StudentClass> students;
	
	public University() {
		this.students = new ArrayList<StudentClass>();
	}
	
	public University(String university_name)
	{
		this();
		this.university_name = university_name;
	}

	public int getUniversity_id() {
		return university_id;
	}

	public void setUniversity_id(int university_id) {
		this.university_id = university_id;
	}

	public String getUniversity_name() {
		return university_name;
	}

	public void setUniversity_name(String university_name) {
		this.university_name = university_name;
	}

	public List<StudentClass> getStudents() {
		return students;
	}

	public void setStudents(List<StudentClass> students) {
		this.students = students;
	}
	
	public void Add_Student_Method(StudentClass student)
	{
		this.students.add(student);
	}
}
