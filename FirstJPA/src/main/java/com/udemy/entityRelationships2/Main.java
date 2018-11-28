package com.udemy.entityRelationships2;

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
		
		University university = new University("EMU");
		
		StudentClass s1 = new StudentClass("Ozer Yavuzaslan");
		s1.setUniversity(university);
		StudentClass s2 = new StudentClass("Caner");
		s2.setUniversity(university);

		university.Add_Student_Method(s1);
		university.Add_Student_Method(s2);

		e_m.persist(university);
		e_m.persist(s1);
		e_m.persist(s2);
		e_m.getTransaction().commit();
		
		e_m.close();
		e_m_f.close();
	}
}
