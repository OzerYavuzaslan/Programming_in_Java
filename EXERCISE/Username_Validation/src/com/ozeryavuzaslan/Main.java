package com.ozeryavuzaslan;

/*
    Have the function CodelandUsernameValidation(str) take the str parameter being passed and determine if the string is a valid username according to the following rules:

    1. The username is between 4 and 25 characters.
    2. It must start with a letter.
    3. It can only contain letters, numbers, and the underscore character.
    4. It cannot end with an underscore character.

    If the username is valid then your program should return the string true, otherwise return the string false.
    Examples
    Input: "aa_"
    Output: false
    Input: "u__hello_world123"
    Output: true
 */

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter a username to validate: ");
        String userName = scanner.nextLine();
        System.out.println("\n" + UsernameValidation(userName));
    }

    public static String UsernameValidation(String userName) {
        int userNameLength = userName.length();
        final char underscore = '_';

        if (userNameLength >= 4 && userNameLength <= 25){
            if (userName.charAt(userNameLength - 1) != underscore){
                if (Character.isLetter(userName.charAt(0))){
                    for (int i = 0; i < userNameLength; i++)
                        if (!Character.isLetter(userName.charAt(i)) && userName.charAt(i) != underscore && !Character.isDigit(userName.charAt(i)))
                            return userName + " is not valid!";

                    return userName + " is a valid username.";
                }
            }
        }

        return userName + " is not valid!";
    }
}