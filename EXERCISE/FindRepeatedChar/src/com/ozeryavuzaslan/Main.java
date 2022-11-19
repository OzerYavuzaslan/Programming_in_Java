package com.ozeryavuzaslan;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter a string which contains repeated chars: ");
        String str = scanner.nextLine();
        Character ch = returnFirstRepeatedChar(str);
        System.out.println();

        if (ch != null)
            System.out.println("First repeated char is " + returnFirstRepeatedChar(str));
        else
            System.out.println("Please enter a string that at least 1 character repeats itself in the string.");
    }

    public static Character returnFirstRepeatedChar(String str){
        HashMap<Character, Integer> hashMap = new HashMap<>();
        char ch;

        for (int i = 0; i < str.length(); i++){
             ch = str.charAt(i);

            if (!hashMap.containsKey(ch))
                hashMap.put(ch, 1);
            else
                return ch;
        }

        return null;
    }
}