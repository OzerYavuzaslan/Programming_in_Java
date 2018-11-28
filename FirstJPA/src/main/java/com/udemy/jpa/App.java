package com.udemy.jpa;

import java.util.Scanner;

public class App
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		Database_Class dbClass = new Database_Class();
		
		dbClass.print_menu();
		int choice = input.nextInt();
		
		while(choice != 0)
		{
			if(choice == 1)
				dbClass.insert_method();
			else if(choice == 2)
			{
				System.out.print("\nEnter number of new inputs: ");
				int num_of_input = input.nextInt();
				dbClass.update_method(num_of_input);
			}
			else if(choice == 3)
			{
				System.out.print("\nEnter number of new inputs: ");
				int num_of_input = input.nextInt();
				dbClass.update_personal_method(num_of_input);
			}
			else if(choice == 4)
				dbClass.read_method();
			else if(choice == 5)
				dbClass.delete_method();
			else if(choice == 6)
				dbClass.list_JPQL_method();
			else if(choice == 7)
				dbClass.list_NativeQuery_method();
			else if(choice == 8)
				dbClass.list_NamedQuery_method();
			
			dbClass.print_menu();
			choice = input.nextInt();
		}
		System.out.println("The program is terminated...");
	}
}
