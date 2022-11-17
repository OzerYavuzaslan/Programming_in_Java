package com.ozeryavuzaslan;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String choice = "";
        Menu.printInitializeMessage();
        ArrayList<Boolean> passwordTypeChoices;

        while (!choice.equalsIgnoreCase("3")){
            Menu.printMenu();
            choice = scanner.nextLine();

            switch (choice){
                case "1":
                    passwordTypeChoices = Menu.addChoice(scanner);
                    Generator generator = new Generator(passwordTypeChoices.get(0), passwordTypeChoices.get(1), passwordTypeChoices.get(2), passwordTypeChoices.get(3), scanner);
                    System.out.println("\nYour password is --> " + generator.getPassword().getPasswordStr());
                    generator.getPassword().calculateScore();
                    System.out.println();

                    break;

                case "2":
                    Menu.printMenu();
                    System.out.println();
                    break;

                case "3":
                    Menu.printQuitMessage();
                    break;

                default:
                    Menu.printWrongChoiceMessage();
                    break;
            }
        }
    }
}