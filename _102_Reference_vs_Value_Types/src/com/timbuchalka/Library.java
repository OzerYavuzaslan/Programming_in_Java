package com.timbuchalka;

import java.util.Arrays;
import java.util.Scanner;

public class Library {
    public static Scanner scanner = new Scanner(System.in);

    public static int[] readIntegers(){
        System.out.print("Enter how many number you will enter: ");
        int num = scanner.nextInt();
        int[] myIntArray = new int[num];

        for (int i = 0; i < num; i++){
            System.out.print("Enter a num: ");
            myIntArray[i] = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
        }

        return myIntArray;
    }

    public static int findMin(int[] myIntArray){
        Arrays.sort(myIntArray);
        return myIntArray[0];
    }

    public static int findMinManually(int[] myIntArray){
        int myMinValue = myIntArray[0];

        for (int i = 1; i < myIntArray.length; i++)
            if (myMinValue > myIntArray[i])
                myMinValue = myIntArray[i];

        return myMinValue;
    }

    public static boolean IsSortingRequested(){
        System.out.print("Do you want to sort the array? (yes for 1, no for 0): ");

        if (scanner.nextInt() == 1)
            return true;

        return false;
    }

    public static int[] reverseMyIntArray(int[] myIntArray, boolean isSortingRequested){
        if (isSortingRequested) {
            Arrays.sort(myIntArray);
            printArray(myIntArray);
        }

        int[] tmpIntArray = new int[myIntArray.length];

        for (int i = 0; i < myIntArray.length; i++)
            tmpIntArray[myIntArray.length - 1 - i] = myIntArray[i];

        return tmpIntArray;
    }

    public static void printArray(int[] myIntArray){
        System.out.println("My Array elements: " + Arrays.toString(myIntArray));
    }
}
