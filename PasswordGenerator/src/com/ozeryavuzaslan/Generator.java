package com.ozeryavuzaslan;

import java.util.Scanner;

public class Generator {
    private Alphabet alphabet;
    private final Password password;

    public Generator(boolean isUpperCase, boolean isLowerCase, boolean isNumber, boolean isSymbol, Scanner scanner){
        setAlphabet(isUpperCase, isLowerCase, isNumber, isSymbol);
        password = generatePassword(scanner);
    }

    private Password generatePassword(Scanner scanner){
        byte length = 0;
        String charLength;
        Menu.printCharacterLengthMessage();

        while (true) {
            charLength = scanner.nextLine();

            for (byte i = 0; i < charLength.length(); i++){
                if (!Character.isDigit(charLength.charAt(i))) {
                    System.out.println(charLength + " is not a valid number! Enter a valid number.");
                    Menu.printCharacterLengthMessage();
                    i = 0;
                    charLength = scanner.nextLine();
                }
            }

            length = Byte.parseByte(charLength);

            if (length < 8 || length > 32) {
                System.out.println("The password length must be greater than 7, and less than 33 characters!");
                Menu.printCharacterLengthMessage();
            } else
                break;
        }

        final StringBuilder passwordStr = new StringBuilder("");
        final int alphabetLength = alphabet.getAlphabet().length();
        int maxLength = alphabetLength - 1;
        byte minLength = 0;
        int rangeLength = maxLength - minLength;

        for (byte i = 0; i < length; i++){
            int index = (int) (Math.random() * rangeLength) + minLength;
            passwordStr.append(getAlphabetInstance().getAlphabet().charAt(index));
        }

        return new Password(passwordStr.toString());
    }

    public Alphabet getAlphabetInstance() {
        return alphabet;
    }

    private void setAlphabet(boolean isUpperCase, boolean isLowerCase, boolean isNumber, boolean isSymbol) {
        this.alphabet = new Alphabet(isUpperCase, isLowerCase, isNumber, isSymbol);
    }

    public Password getPassword() {
        return password;
    }
}
