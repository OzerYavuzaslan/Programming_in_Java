package com.udemy.jpa;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Database_Class
{
	public Scanner create_scanner()
	{
		Scanner input = new Scanner(System.in);
		return input;
	}
	
	public void insert_method()
	{
		System.out.print("\nEnter address: ");
		String address = create_scanner().nextLine();
		
		System.out.print("Enter age: ");
		int age = create_scanner().nextInt();
		create_scanner().nextLine();
		
		System.out.print("Enter name: ");
		String name = create_scanner().nextLine();

		Student student = new Student(age, name, address);

		EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("com.ozeryavuzaslan.jpa");
		EntityManager e_m = e_m_f.createEntityManager();
		e_m.getTransaction().begin();
		e_m.persist(student);
		e_m.getTransaction().commit();
		e_m.close();
		e_m_f.close();
	}
	
	public void update_method(int num_of_insert)
	{
		for(int i = 0; i < num_of_insert; i++)
		{
			System.out.print("\nEnter address: ");
			String address = create_scanner().nextLine();
			
			System.out.print("Enter age: ");
			int age = create_scanner().nextInt();
			create_scanner().nextLine();
			
			System.out.print("Enter name: ");
			String name = create_scanner().nextLine();

			Student student = new Student(age, name, address);

			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("com.ozeryavuzaslan2.jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			e_m.persist(student);
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
	}
	
	public void update_personal_method(int num_of_insert)
	{
		for(int i = 0; i < num_of_insert; i++)
		{
			System.out.print("\nEnter name: ");
			String name = create_scanner().nextLine();

			System.out.print("Enter email: ");
			String email = create_scanner().nextLine();
			
			System.out.print("Enter age: ");
			int age = create_scanner().nextInt();

			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("personal_jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Person person = new Person(name, email, age);
			e_m.persist(person);
			
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
	}
	
	public void read_method()
	{
		System.out.print("\nRead from database."
				+ "\n1. Student Table."
				+ "\n2. Person Table."
				+ "\nWhat is your choice?: ");
		int choice = create_scanner().nextInt();
		
		System.out.print("\nEnter id: ");
		int id = create_scanner().nextInt();

		if(choice == 1)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("com.ozeryavuzaslan2.jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Student student = e_m.find(Student.class, id);
			System.out.println(student);
			
			e_m.close();
			e_m_f.close();
		}
		else if(choice == 2)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("personal_jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Person person = e_m.find(Person.class, id);
			System.out.println(person);
			
			e_m.close();
			e_m_f.close();
		}
	}
	
	public void delete_method()
	{
		System.out.print("\nWhere do you want to delete a data?"
				+ "\n1. Student Table."
				+ "\n2. Person Table."
				+ "\nEnter your choice:");
		int choice = create_scanner().nextInt();
		
		System.out.print("\nEnter id to delete: ");
		int id = create_scanner().nextInt();
		
		if(choice == 1)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("com.ozeryavuzaslan2.jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Student student = e_m.find(Student.class, id);
			e_m.remove(student);
						
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
		else if(choice == 2)
		{			
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("personal_jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Person person = e_m.find(Person.class, id);
			e_m.remove(person);
		
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
	}
	
	public void list_JPQL_method()
	{
		System.out.print("\nEnter the table."
				+ "\n1. Student Table."
				+ "\n2. Person Table."
				+ "\nWhat is your choice?: ");
		int choice = create_scanner().nextInt();
		
		if(choice == 1)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("com.ozeryavuzaslan2.jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Query query = e_m.createQuery("SELECT student FROM Student student");
			List<Student> students = (List<Student>) query.getResultList();
			
			for(Student s: students)
				System.out.println(s);
		
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
		else if(choice == 2)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("personal_jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Query query = e_m.createQuery("SELECT person FROM Person person");
			
			/*
			Query query = e_m.createQuery("SELECT person FROM Person person WHERE person.age BETWEEN 17 AND 25");
			Query query = e_m.createQuery("SELECT person FROM Person person WHERE person.age >=25");
			Query query = e_m.createQuery("SELECT person FROM Person person WHERE person.name LIKE '%n 4'");
			Query query = e_m.createQuery("SELECT person FROM Person person ORDER BY person.age ASC);
			Query query = e_m.createQuery("SELECT person FROM Person person ORDER BY person.age DESC);
			Query query = e_m.createQuery("SELECT person FROM Person person ORDER BY name.age ASC);
			Query query = e_m.createQuery("SELECT person FROM Person person ORDER BY name.age DESC);
			*/
			
			List<Person> persons = (List<Person>) query.getResultList();
			
			/*
			for(Person p: persons)
				System.out.println(p);
			 */
			
			for(int i = 0; i < persons.size(); i++)
				System.out.println(persons.get(i));
		
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
	}
	
	public void list_NativeQuery_method()
	{
		System.out.print("\nEnter the table."
				+ "\n1. Student Table."
				+ "\n2. Person Table."
				+ "\nWhat is your choice?: ");
		int choice = create_scanner().nextInt();
		
		if(choice == 1)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("com.ozeryavuzaslan2.jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Query query = e_m.createNativeQuery("SELECT * FROM student", Student.class);
			List<Student> students = (List<Student>) query.getResultList();

			for(int i = 0; i < students.size(); i++)
				System.out.println(students.get(i));
		
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
		else if(choice == 2)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("personal_jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Query query = e_m.createNativeQuery("SELECT * FROM PERSON_TABLE", Person.class);
			
			/*
			Query query = e_m.createNativeQuery("SELECT * FROM PERSON_TABLE WHERE age BETWEEN 15 and 25", Person.class);
			 */
			
			List<Person> persons = (List<Person>) query.getResultList();
			
			for(int i = 0; i < persons.size(); i++)
				System.out.println(persons.get(i));
		
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
	}
	
	public void list_NamedQuery_method()
	{
		System.out.print("\nEnter the table."
				+ "\n1. Student Table."
				+ "\n2. Person Table."
				+ "\nWhat is your choice?: ");
		int choice = create_scanner().nextInt();
		
		if(choice == 1)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("com.ozeryavuzaslan2.jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			Query query = e_m.createNamedQuery("student.getAll");
			List<Student> students = (List<Student>) query.getResultList();

			for(int i = 0; i < students.size(); i++)
				System.out.println(students.get(i));
		
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
		else if(choice == 2)
		{
			EntityManagerFactory e_m_f = Persistence.createEntityManagerFactory("personal_jpa");
			EntityManager e_m = e_m_f.createEntityManager();
			e_m.getTransaction().begin();
			
			TypedQuery<Person> query = e_m.createNamedQuery("person.getAll", Person.class);
			List<Person> persons = query.getResultList(); //TypedQuery kullaninca cast etmeye gerek yok.
			
			/*
			TypedQuery<Person> query = e_m.createNamedQuery("person.getPersonById", Person.class);
			query.setParameter("id", 1);
			*/
			/*
			Query query = e_m.createNamedQuery("person.getAll");
			*/
			
			for(int i = 0; i < persons.size(); i++)
				System.out.println(persons.get(i));
		
			e_m.getTransaction().commit();
			e_m.close();
			e_m_f.close();
		}
	}
	
	public void print_menu()
	{
		System.out.print("\n1. Insert a data by recreating the table."
				+ "\n2. Update the existing table."
				+ "\n3. Insert into personal table."
				+ "\n4. Read from tables."
				+ "\n5. Delete a data."
				+ "\n6. List data by using JPQL."
				+ "\n7. List data by using native query."
				+ "\n8. List data by using named queries."
				+ "\nWhat is your choice? (0 to terminate the program): ");
	}
}
