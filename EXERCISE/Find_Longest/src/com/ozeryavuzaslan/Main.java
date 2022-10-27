package com.ozeryavuzaslan;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Enter a sentence to find the longest word: ");
        String sentence = scanner.nextLine();
        System.out.println("The longest word is: " + longestWord(sentence));

        //ali ozer4343 efe banu2121 mehmet!! ziya&muhammedin yavuzaslan
    }

    public static String longestWord(String sentence){
        String longestWord = "";
        String tmpLongestWord = "";
        int longestWordLength = 0;
        int tmpLongestWordLength = 0;
        char ch;

        for (int i = 0; i < sentence.length(); i++){
            ch = sentence.charAt(i);

            if (isAValidLetter(ch)){
                tmpLongestWord = tmpLongestWord + ch;
                tmpLongestWordLength += 1;
            }
            else {
                if (tmpLongestWordLength > longestWordLength) {
                    longestWord = tmpLongestWord;
                    longestWordLength = tmpLongestWordLength;
                }

                tmpLongestWord = "";
                tmpLongestWordLength = 0;
            }
        }

        if (tmpLongestWordLength > longestWordLength)
            longestWord = tmpLongestWord;

        return longestWord;
    }

    public static boolean isAValidLetter(char ch){
        switch (ch){
            case '.': case ',': case ';': case ':': case '!': case '\'': case '^':
            case '+': case '%': case '&': case '/': case '"': case '-': case '*':
            case '{': case '}': case '_': case '$': case '[': case ']': case '=':
            case '|': case '<': case '>': case '~': case ' ': 
                return false;

            default:
                return true;
        }
    }
}