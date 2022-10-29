package com.ozeryavuzaslan;

import java.util.Scanner;

/*
    Have the function QuestionsMarks(str) take the str string parameter, which will contain single digit numbers,
    letters, and question marks, and check if there are exactly 3 question marks between every pair of two numbers
    that add up to 10. If so, then your program should return the string true, otherwise it should return the string
    false. If there aren't any two numbers that add up to 10 in the string, then your program should return false as well.

    For example: if str is "arrb6???4xxbl5???eee5" then your program should return true because there are exactly 3 question
    marks between 6 and 4, and 3 question marks between 5 and 5 at the end of the string.

    Input: "aa6?9"
    Output: false
    Input: "acc?7??sss?3rr1??????5"
    Output: true

    1?aaa??3???7
    asd?1??2Aasdawa?asdwawda??8
    1??a?3??4bcd??ef5????2
    1??a?3??4bcd??ef5????2g?h?j?8
 */

class Main {
    public static String QuestionMarks(String str) {
        char ch;
        int numberCnt = 0;
        int sumOfNumbers = 0;
        int questionMarkCnt = 0;
        final char questionMark = '?';
        int lengthOfStr = str.length();
        int continueIndexForFirstLoop = 0;

        for (int i = 0; i < lengthOfStr; i++) {
            ch = str.charAt(i);

            if (Character.isDigit(ch)) {
                sumOfNumbers += Integer.parseInt(String.valueOf(ch));
                numberCnt += 1;

                for (int j = i + 1; j < lengthOfStr; j++){
                    ch = str.charAt(j);

                    if (Character.isDigit(ch)) {
                        sumOfNumbers += Integer.parseInt(String.valueOf(ch));
                        numberCnt += 1;

                        if (numberCnt == 2)
                            continueIndexForFirstLoop = j - 1;
                    }

                    if (ch == questionMark) {
                        questionMarkCnt += 1;

                        if (questionMarkCnt > 3)
                            continueIndexForFirstLoop = j;
                    }

                    if (questionMarkCnt == 3 && sumOfNumbers == 10 && numberCnt == 2)
                        return "True";

                    if (questionMarkCnt > 3 || sumOfNumbers > 10 || (numberCnt == 2 && (sumOfNumbers != 10 || questionMarkCnt != 3))){
                        sumOfNumbers = 0;
                        questionMarkCnt = 0;
                        numberCnt = 0;
                        i = continueIndexForFirstLoop;
                        break;
                    }
                }
            }
        }

        return "False";
    }

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string to answer question mark problem: ");
        System.out.print(QuestionMarks(scanner.nextLine()));
    }
}