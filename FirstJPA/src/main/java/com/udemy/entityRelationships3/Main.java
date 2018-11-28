package com.udemy.entityRelationships3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main
{
	public static void main(String[] args)
	{
		EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("personal_jpa");
		EntityManager e_m = e_m_f.createEntityManager();
		e_m.getTransaction().begin();

		Professor prof1 = new Professor("Stewen Hawking");
		Professor prof2 = new Professor("Albert Einstein");

		ResearchProject project1 = new ResearchProject("Black Hole Project");
		ResearchProject project2 = new ResearchProject("Quantum Field Theory Project");
		ResearchProject project3 = new ResearchProject("Thermodynamic Fluctuations Project");

		prof1.AssignProjectMethod(project1);
		prof1.AssignProjectMethod(project2);
		prof2.AssignProjectMethod(project3);
		prof2.AssignProjectMethod(project1);

		project1.AssignProfessorMethod(prof1);
		project1.AssignProfessorMethod(prof2);
		project2.AssignProfessorMethod(prof1);
		project3.AssignProfessorMethod(prof2);

		e_m.persist(prof1);
		e_m.persist(prof2);
		e_m.persist(project1);
		e_m.persist(project2);
		e_m.persist(project3);
		e_m.getTransaction().commit();
		
		e_m.close();
		e_m_f.close();
	}
}
