package com.ozeryavuzaslan;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static String  evenOrNot(long num){
        String strNum = String.valueOf(num);
        char ch;

        for (int i = 0; i < strNum.length(); i++){
            ch = strNum.charAt(i);

            if (Integer.parseInt(String.valueOf(ch)) % 2 != 0)
                return "odd";
        }

        return "even";
    }

    public static void main(String[] args) {
        System.out.print("Enter a number to find its every digit is even or not: ");
        long num = scanner.nextLong();
        scanner.nextLine();
        String resultStr = evenOrNot(num);
        System.out.println("\n" + num + "'s every digit is " + resultStr);
        scanner.close();
    }
}