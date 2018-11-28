package com.udemy.inheritance;

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
		
		Car car = new Car("BMW", 400);
		Bus bus = new Bus("Mercedes", 200);

		e_m.persist(car);
		e_m.persist(bus);
		e_m.getTransaction().commit();
		
		e_m.close();
		e_m_f.close();
	}
}
