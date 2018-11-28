package com.udemy.inheritanceTablePerClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main
{
	public static void main(String[] args)
	{
		EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("vehicle2_jpa");
		EntityManager e_m = e_m_f.createEntityManager();
		e_m.getTransaction().begin();
		
		Car3 car = new Car3("Car1", 300);
		Bus3 bus = new Bus3("Bus1", 150);

		e_m.persist(car);
		e_m.persist(bus);
		e_m.getTransaction().commit();
		
		e_m.close();
		e_m_f.close();
	}
}
