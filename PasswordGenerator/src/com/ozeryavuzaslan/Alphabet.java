package com.ozeryavuzaslan;

public class Alphabet {
    public static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz"; // 1 point
    public static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 2 points
    public static final String NUMBERS = "1234567890"; // 3 points
    public static final String SYMBOLS = "!@#$%^&*()-_=+\\/~?"; // 4 points
    private final StringBuilder strPool;

    public Alphabet(boolean isUpperCase, boolean isLowerCase, boolean isNumber, boolean isSymbol) {
        strPool = new StringBuilder();

        if (isUpperCase)
            strPool.append(UPPERCASE_LETTERS);

        if (isLowerCase)
            strPool.append(LOWERCASE_LETTERS);

        if (isNumber)
            strPool.append(NUMBERS);

        if (isSymbol)
            strPool.append(SYMBOLS);
    }

    public String getAlphabet() {
        return strPool.toString();
    }
}
