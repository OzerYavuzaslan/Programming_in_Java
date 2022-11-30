package com.ozeryavuzaslan;

public class DrawRightTriangle {
    public static void drawRightTriangle(int numOfLines){
        for (int i = 1; i <= numOfLines; i++) {
            for (int j = 0; j < i; j++)
                System.out.print("*");

            System.out.println();
        }
    }

    public static void drawReverseRightTriangle(int numOfLines){
        for (int i = 0; i < numOfLines; i++) {
            for (int j = 1; j < numOfLines - i; j++)
                System.out.print(" ");

            for (int k = 0; k <= i; k++)
                System.out.print("*");

            System.out.println();
        }
    }
}
