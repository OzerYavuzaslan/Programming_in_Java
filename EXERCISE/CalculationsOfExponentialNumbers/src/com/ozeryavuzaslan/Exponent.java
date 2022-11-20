package com.ozeryavuzaslan;

public class Exponent {
    private int base;
    private int power;

    public Exponent(int base, int power){
        setBase(base);
        setPower(power);
    }

    public double calculateNPowerM(){
        if (getBase() == 0)
            return 0;
        else if (getPower() == 0)
            return 1;
        else if (getPower() == 1)
            return getBase();
        else if (getPower() == -1)
            return 1 / (double) getBase();

        int tmpPower = returnPower(getPower());
        double result = getBase();

        for (int i = 1; i < tmpPower; i++)
            result = result * getBase();

        if (getPower() < 0)
            return 1 / result;

        return result;
    }

    private double calculateNPowerM(int power){
        if (getBase() == 0)
            return 0;
        if (power == 0)
            return 1;
        else if (power == 1)
            return getBase();
        else if (power == -1)
            return 1 / (double) getBase();

        int tmpPower = returnPower(power);
        double result = getBase();

        for (int i = 1; i < tmpPower; i++)
            result = result * getBase();

        if (power < 0)
            return 1 / result;

        return result;
    }

    public double calculateChoice2(){
        double sum = 0;
        int tmpPower = returnPower(getPower());

        for (int i = 0; i <= tmpPower; i++)
            if (getPower() < 0)
                sum += calculateNPowerM(i * (-1));
            else
                sum += calculateNPowerM(i);

        return sum;
    }

    private int returnPower(int power){
        if (power < 0)
            return power * (-1);
        else
            return power;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public static void printMenu(){
        System.out.println("Enter 1 to Calculate n^m");
        System.out.println("Enter 2 to Calculate n^0 + n^1 + n^2 + ... + n^m \n\tFor example, m is 4 --> n^0 + n^1 + n^2 + n^3 + n^4");
        System.out.println("Enter 3 to QUIT.");
        System.out.println("Both power and base numbers can be negative numbers.");
        System.out.print("Choice: ");
    }
}
