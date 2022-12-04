package com.ozeryavuzaslan;

public class Random {
    public static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "1234567890";
    private final StringBuilder strBuilder;

    public Random(){
        strBuilder = new StringBuilder();
        strBuilder.append(UPPERCASE_LETTERS);
        strBuilder.append(NUMBERS);
    }

    private StringBuilder getStrBuilder() {
        return strBuilder;
    }

    public String generateRandomValue(){
        final StringBuilder randomStrBuilder = new StringBuilder("");
        final int alphabetLength = getStrBuilder().toString().length();
        int maxLength = alphabetLength - 1;
        byte minLength = 0;

        for (byte i = 0; i < 10; i++){
            int index = (int) (Math.random() * maxLength) + minLength;
            randomStrBuilder.append(getStrBuilder().toString().charAt(index));
        }

        return randomStrBuilder.toString();
    }
}
