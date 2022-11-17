package com.ozeryavuzaslan;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final String YES = "Y";
    private static final String NO = "N";
    public static void printInitializeMessage(){
        System.out.println("This is a random password generator program.");
    }

    public static void printMenu(){
        System.out.println("Enter 1 to - Password Generator.");
        System.out.println("Enter 2 to - Print the Menu.");
        System.out.println("Enter 3 to - Quit the Program.");
        printChoiceStr();
    }

    public static void printQuitMessage(){
        System.out.println("Exiting the program...");
    }

    public static void printWrongChoiceMessage(){
        System.out.println("Wrong input choice! Enter a valid choice between 1 and 4.");
    }

    public static void printUppercaseQuestion(){
        System.out.println("Do you wish to include uppercase letters? y/n");
        printChoiceStr();
    }

    public static void printLowercaseQuestion(){
        System.out.println("Do you wish to include lowercase letters? y/n");
        printChoiceStr();
    }

    public static void printNumberQuestion(){
        System.out.println("Do you wish to include numbers? y/n");
        printChoiceStr();
    }

    public static void printSymbolQuestion(){
        System.out.println("Do you wish to include symbols? y/n");
        printChoiceStr();
    }

    private static void printChoiceStr(){
        System.out.print("Choice: ");
    }

    public static boolean checkAnswer(String answer) {
        if (!answer.equalsIgnoreCase(YES)){
            if (!answer.equalsIgnoreCase(NO)) {
                System.out.println("You've entered something wrong: " + answer);
                return false;
            }
        }

        return true;
    }

    public static ArrayList<Boolean> addChoice(Scanner scanner){
        String answer;
        byte questionCnt = 1;
        boolean loopFlag = true;
        boolean isNumber = false;
        boolean isSymbols = false;
        boolean isLowercase = false;
        boolean isUppercase = false;
        ArrayList<Boolean> answerList = new ArrayList<>();

        while (loopFlag){
            switch (questionCnt){
                case 1:
                    printLowercaseQuestion();
                    answer = scanner.nextLine().toUpperCase();

                    if (checkAnswer(answer)) {
                        isLowercase = answer.equalsIgnoreCase(YES);
                        questionCnt += 1;
                    }
                    else
                        continue;

                case 2:
                    printUppercaseQuestion();
                    answer = scanner.nextLine().toUpperCase();

                    if (checkAnswer(answer)) {
                        isUppercase = answer.equalsIgnoreCase(YES);
                        questionCnt += 1;
                    }
                    else
                        continue;

                case 3:
                    printNumberQuestion();
                    answer = scanner.nextLine().toUpperCase();

                    if (checkAnswer(answer)) {
                        isNumber = answer.equalsIgnoreCase(YES);
                        questionCnt += 1;
                    }
                    else
                        continue;

                case 4:
                    printSymbolQuestion();
                    answer = scanner.nextLine().toUpperCase();

                    if (checkAnswer(answer)) {
                        isSymbols = answer.equalsIgnoreCase(YES);
                        questionCnt += 1;
                    }
                    else
                        continue;
            }

            if (!isLowercase && !isUppercase && !isNumber && !isSymbols) {
                System.out.println("At least you have to answer YES one of the character requests.");
                questionCnt = 1;
            } else
                loopFlag = false;
        }

        answerList.add(isLowercase);
        answerList.add(isUppercase);
        answerList.add(isNumber);
        answerList.add(isSymbols);
        return answerList;
    }

    public static void printCharacterLengthMessage(){
        System.out.println("Enter how many characters do you like to have. The length must be greater than 7, and less than 33 characters.");
        System.out.print("Answer: ");
    }
}


