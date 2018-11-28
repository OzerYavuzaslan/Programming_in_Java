package com.udemy.entityRelationships1;

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
		
		Employee emp = new Employee();
		emp.setEmp_name("Ozer Yavuzaslan");
		
		Address address = new Address();
		address.setAddress_name("Ugur Mumcu Mah.");
		address.setZip_code(34543);
		
		emp.setAddress(address);
		address.setEmp(emp);

		e_m.persist(emp);
		e_m.persist(address);		
		e_m.getTransaction().commit();
		
		e_m.close();
		e_m_f.close();
	}
}
