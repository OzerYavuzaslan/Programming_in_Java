package com.ozeryavuzaslan;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter a number to calculate factorial: ");
        int num = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nEntered number is " + num);

        long factorial = 1;

        for (int i = num; i > 0; i--)
            factorial *= i;

        System.out.println("Factorial of " + num + " is: " + factorial);

        scanner.close();
    }
}