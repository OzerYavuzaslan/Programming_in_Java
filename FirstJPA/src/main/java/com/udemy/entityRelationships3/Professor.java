package com.udemy.entityRelationships3;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="PROFESSOR")
public class Professor
{
	@Id
	@GeneratedValue
	@Column(name="id")
	private int proffessor_id;

	private String professor_name;
	
	//When getter is  called, the data will be fetched. Cascade will remove all references from the list, when we remove an object.
	@ManyToMany(mappedBy="professors", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<ResearchProject> projects;
	
	public Professor() {
		this.projects = new ArrayList<ResearchProject>();
	}
	
	public Professor(String professor_name)
	{
		this();
		this.professor_name = professor_name;
	}

	public int getProffessor_id() {
		return proffessor_id;
	}

	public void setProffessor_id(int proffessor_id) {
		this.proffessor_id = proffessor_id;
	}

	public String getProfessor_name() {
		return professor_name;
	}

	public void setProfessor_name(String professor_name) {
		this.professor_name = professor_name;
	}

	public List<ResearchProject> getProjects() {
		return projects;
	}

	public void setProjects(List<ResearchProject> projects) {
		this.projects = projects;
	}
	
	public void AssignProjectMethod(ResearchProject project)
	{
		this.projects.add(project);
	}
}
