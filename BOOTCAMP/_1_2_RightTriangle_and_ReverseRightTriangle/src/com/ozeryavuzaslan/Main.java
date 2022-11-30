package com.ozeryavuzaslan;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int numOfLines = 0;
        boolean loopFlag = true;

        while (loopFlag) {
            System.out.print("Enter how many lines that you want to draw: ");
            String inputStr = scanner.nextLine();

            for (int i = 0; i < inputStr.length(); i++) {
                char ch = inputStr.charAt(i);

                if (Character.isDigit(ch)) {
                    if (i == inputStr.length() - 1) {
                        numOfLines = Integer.parseInt(inputStr);
                        loopFlag = false;
                    }
                } else if (i == inputStr.length() - 1)
                    System.out.println("You must enter an integer value! You've entered: " + inputStr);
            }
        }

        DrawRightTriangle.drawRightTriangle(numOfLines);
        DrawRightTriangle.drawReverseRightTriangle(numOfLines);
        scanner.close();
    }
}