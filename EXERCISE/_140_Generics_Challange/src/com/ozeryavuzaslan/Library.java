package com.ozeryavuzaslan;

import java.util.Random;

public class Library {
    public static int generateRandomValues(){
        Random randomValue = new Random();
        int upperBound = 10;
        return randomValue.nextInt(upperBound);
    }
}
