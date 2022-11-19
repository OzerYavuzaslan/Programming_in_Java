package com.ozeryavuzaslan;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter a string which contains repeated chars: ");
        String str = scanner.nextLine();
        String returnedStr = returnRepeatedChars(str);
        System.out.println();

        if (!returnedStr.isEmpty())
            System.out.println("Repeated character(s) is/are " + returnedStr);
        else
            System.out.println("Please enter a string that at least 1 character repeats itself in the string.");
    }

    public static String returnRepeatedChars(String str){
        char ch;
        StringBuilder tmpStr = new StringBuilder();
        HashMap<Character, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < str.length(); i++){
            ch = str.charAt(i);

            if (!hashMap.containsKey(ch))
                hashMap.put(ch, 1);
            else
                tmpStr.append(ch);
        }

        return tmpStr.toString();
    }
}