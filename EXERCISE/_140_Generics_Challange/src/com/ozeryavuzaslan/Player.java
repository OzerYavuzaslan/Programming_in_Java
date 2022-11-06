package com.ozeryavuzaslan;

public abstract class Player {
    private String name;
    private int age;
    private double weight;

    public Player(String name, int age, double weight) {
        setName(name);
        setAge(age);
        setWeight(weight);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
