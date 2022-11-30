package com.ozeryavuzaslan;

public class Calculation {
    public static void findPalindromicNumber(int number){
        long tmpNumber = number;
        long tmpNumber2;
        long sum;
        byte counter = 0;

        while(true) {
            tmpNumber2 = reverseInteger(tmpNumber);
            sum = tmpNumber + tmpNumber2;

            System.out.print(tmpNumber + " + " + tmpNumber2 + " = " + sum + " ---> ");
            counter++;

            if (sum == reverseInteger(sum)) {
                System.out.println("is a palindromic number. " + counter + " time(s) took to find that palindromic number.");
                break;
            } else
                System.out.println("is NOT a palindromic number.");

            tmpNumber = sum;
        }
    }

    public static long reverseInteger(long number){
        String tmpNumStr = String.valueOf(number);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(tmpNumStr);
        strBuilder.reverse();
        tmpNumStr = strBuilder.toString();
        return Long.parseLong(tmpNumStr);
    }
}
