package com.ozeryavuzaslan;

public class Password {
    private int length;
    private String passwordStr;
    private int strengthScore;

    public Password(String passwordStr){
        setPasswordStr(passwordStr);
        setLength(passwordStr.length());
    }

    private byte findCharType(char ch){
        byte tmpVal;

        if ((int) ch >= 97 && (int) ch <= 122)
            tmpVal = 1;
        else if ((int) ch >= 65 && (int) ch <= 90)
            tmpVal = 2;
        else if ((int) ch >= 48 && (int) ch <= 57)
            tmpVal = 3;
        else
            tmpVal = 4;

        return tmpVal;
    }

    private int passwordStrength(){
        boolean isLowercase = false;
        boolean isUppercase = false;
        boolean isNumber = false;
        boolean isSymbol = false;
        byte tmpStrengthScore = 0;

        for (int i = 0; i < getLength(); i++){
            byte charType = findCharType(getPasswordStr().charAt(i));

            if (charType == 1 && !isLowercase) {
                tmpStrengthScore += charType;
                isLowercase = true;
            }
            else if (charType == 2 && !isUppercase) {
                tmpStrengthScore += charType;
                isUppercase = true;
            }
            else if (charType == 3 && !isNumber) {
                tmpStrengthScore += charType;
                isNumber = true;
            }
            else if (charType == 4 && !isSymbol) {
                tmpStrengthScore += charType;
                isSymbol = true;
            }

            if (isLowercase && isUppercase && isNumber && isSymbol)
                break;
        }

        if (getLength() <= 16 && getLength() >= 8)
            tmpStrengthScore += 2;

        if (getLength() <= 32 && getLength() > 16)
            tmpStrengthScore += 4;

        return tmpStrengthScore;
    }

    public void calculateScore(){
        setStrengthScore(passwordStrength());

        if (getStrengthScore() < 4)
            System.out.println("This is a very weak password!");
        else if (getStrengthScore() < 8)
            System.out.println("This is a weak password.");
        else if (getStrengthScore() < 12)
            System.out.println("This is a good password.");
        else
            System.out.println("This is a very good password.");
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPasswordStr() {
        return passwordStr;
    }

    public void setPasswordStr(String passwordStr) {
        this.passwordStr = passwordStr;
    }

    public int getStrengthScore() {
        return strengthScore;
    }

    public void setStrengthScore(int strengthScore) {
        this.strengthScore = strengthScore;
    }

    @Override
    public String toString() {
        return getPasswordStr();
    }
}
