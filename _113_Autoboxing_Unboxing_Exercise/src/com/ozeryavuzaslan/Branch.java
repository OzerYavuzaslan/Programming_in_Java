package com.ozeryavuzaslan;

import java.util.ArrayList;

public class Branch {
    private String name;
    private ArrayList<Customer> customerArrayList;

    public Branch(String name) {
        setName(name);
        this.customerArrayList = new ArrayList<Customer>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Customer> getCustomerArrayList() {
        return customerArrayList;
    }

    public boolean newCustomer(String customerName, double initialAmount){
        if (findCustomer(customerName) == null) {
            this.customerArrayList.add(new Customer(customerName, initialAmount));
            return true;
        }

        return false;
    }

    public boolean addCustomerTransaction(String customerName, double amount){
        Customer existingCustomer = findCustomer(customerName);

        if (existingCustomer != null) {
            existingCustomer.addTransaction(amount);
            return true;
        }

        return false;
    }

    private Customer findCustomer(String customerName){
        int customerIndex = findCustomerIndex(customerName);

        if (customerIndex >= 0)
            return this.customerArrayList.get(customerIndex);

        return null;
    }

    private int findCustomerIndex(String customerName){
        for (int i = 0; i < this.customerArrayList.size(); i++)
            if (this.customerArrayList.get(i).getName().equals(customerName))
                return i;

        return -1;
    }
}
