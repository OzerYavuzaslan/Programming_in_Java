package com.udemy.entityRelationships3;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="PROJECTS")
public class ResearchProject
{
	@Id
	@GeneratedValue
	@Column(name="id")
	private int projects_id;

	private String project_name;
	
	@ManyToMany
	@JoinTable(name="PROF_PROJECT", joinColumns=@JoinColumn(name="project_id"), //primary key
	inverseJoinColumns = @JoinColumn(name="professor_id")) //Foreign key
	private List<Professor> professors;
	
	public ResearchProject() {
		this.professors = new ArrayList<Professor>();
	}
	
	public ResearchProject(String project_name)
	{
		this();
		this.project_name = project_name;
	}

	public int getProjects_id() {
		return projects_id;
	}

	public void setProjects_id(int projects_id) {
		this.projects_id = projects_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}
	
	public void AssignProfessorMethod(Professor professor)
	{
		this.professors.add(professor);
	}
}
