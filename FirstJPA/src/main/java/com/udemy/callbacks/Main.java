package com.udemy.callbacks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main
{
	public static void main(String[] args)
	{
		EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("vehicle_jpa");
		EntityManager e_m = e_m_f.createEntityManager();
		e_m.getTransaction().begin();
		
		Article article = new Article("Albert Einstein - Relativity");
		e_m.persist(article);
		
		e_m.getTransaction().commit();
		
		e_m.close();
		e_m_f.close();
	}
}
