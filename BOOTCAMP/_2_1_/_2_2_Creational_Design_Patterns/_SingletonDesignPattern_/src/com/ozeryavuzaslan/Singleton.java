package com.ozeryavuzaslan;

public class Singleton {
    // Static variable reference of single_instance
    // of type Singleton
    private static Singleton singleInstance = null;
    // Declaring a variable of type String
    public String str;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private Singleton() {
        str = "Hello I am a string part of Singleton class";
    }

    // Static method
    // Static method to create instance of Singleton class
    public static Singleton getInstance()
    {
        if (singleInstance == null)
            singleInstance = new Singleton();

        return singleInstance;
    }
}
