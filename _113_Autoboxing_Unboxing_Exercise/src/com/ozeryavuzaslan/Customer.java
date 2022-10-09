package com.ozeryavuzaslan;

import java.util.ArrayList;

public class Customer {
    private String name;
    private ArrayList<Double> transactionAmountArrayList;

    public Customer(String name, double transactionAmount){
        setName(name);
        this.transactionAmountArrayList = new ArrayList<Double>();
        addTransaction(transactionAmount);
    }

    public void addTransaction(double transactionAmount){
        this.transactionAmountArrayList.add(transactionAmount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Double> getTransactionAmountArrayList() {
        return transactionAmountArrayList;
    }
}
