package com.ozeryavuzaslan;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter a string to reverse it: ");
        String str = scanner.nextLine();
        System.out.println();

        System.out.println("Entered String: " + str);

        int strLength      = str.length();
        String reversedStr = "";
        ArrayList<Character> charArrayListList = new ArrayList<Character>();

        for (int i = 0; i < strLength; i++){
            charArrayListList.add(str.charAt(i));
            System.out.println("Char at(" + i + "): " + charArrayListList.get(i));
        }

        for (int i = strLength - 1; i >= 0; i--)
            reversedStr += charArrayListList.get(i);

        System.out.println("Reversed Str: " + reversedStr);
        scanner.close();
    }
}