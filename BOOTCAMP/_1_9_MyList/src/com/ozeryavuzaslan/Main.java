package com.ozeryavuzaslan;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean loopFlag = true;
        MyList myList = new MyList();

        do {
            MyList.printMenu();
            String choice = scanner.nextLine();
            System.out.println();
            String str = "";

            switch (choice){
                case "1":
                    System.out.print("Enter a string: ");
                    str = scanner.nextLine();
                    System.out.println();

                    if (!myList.add(str))
                        System.out.println("First character of the string must start with 'C' letter. This is why that " + str + " value can't be added into the array.\n");
                    else
                        System.out.println("Your string value is successfully added.\n");
                    break;

                case "2":
                    System.out.print("Enter the index of the value that you'd like to delete: ");
                    str = scanner.nextLine();

                    try {
                        int tmpIndex = Integer.parseInt(str);
                        myList.delete(tmpIndex);
                    }
                    catch (Exception exception){
                        exception.printStackTrace();
                    }
                    break;

                case "3":
                    System.out.print("Enter a string value to delete from array: ");
                    str = scanner.nextLine();
                    System.out.println();
                    myList.delete(str);
                    break;

                case "4":
                    myList.printElements();
                    break;

                case "5":
                    myList.printNumberOfElementsAndArraySize();
                    break;

                case "6":
                    System.out.println("The program is closing...");
                    loopFlag = false;
                    break;

                default:
                    System.out.println("You've entered wrong key. Your key: " + choice + "\n");
                    break;
            }
        } while (loopFlag);
    }
}