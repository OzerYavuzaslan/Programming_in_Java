package com.ozeryavuzaslan;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final char EXIT = '3';

    public static void main(String[] args) {
        String choice = "";
        int base;
        int power;

        while (true){
            Exponent.printMenu();
            choice = scanner.nextLine();

            if (choice.equalsIgnoreCase(String.valueOf(EXIT))){
                System.out.println("Closing the program...");
                break;
            }

            System.out.print("\nEnter a BASE value: ");
            base = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter a POWER value: ");
            power = scanner.nextInt();
            scanner.nextLine();

            Exponent calculation = new Exponent(base, power);
            System.out.println();

            switch (choice){
                case "1":
                    System.out.println("n power m is: " + calculation.calculateNPowerM());
                    break;

                case "2":
                    System.out.println("n^0 + n^1 + n^2 + ... + n^m --> " + calculation.calculateChoice2());
                    break;
            }

            System.out.println();
        }

        scanner.close();
    }
}