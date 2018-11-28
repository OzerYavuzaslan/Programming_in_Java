package com.udemy.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="person_table")
@NamedQueries({ @NamedQuery(name="person.getAll", query="SELECT p FROM Person p"),
				@NamedQuery(name="person.getPersonById", query="SELECT p FROM Person p WHERE p.id =:id")})
public class Person
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	private String email;
	private int age;
	
	public Person()
	{
		
	}

	public Person(String name, String email, int age)
	{
		this.name = name;
		this.email = email;
		this.age = age;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString()
	{
		return this.name + " - " + this.age + " - " + this.email;
	}
}
