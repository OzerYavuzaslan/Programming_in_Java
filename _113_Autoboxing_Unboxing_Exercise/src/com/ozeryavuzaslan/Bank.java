package com.ozeryavuzaslan;

import java.util.ArrayList;

public class Bank {
    private String name;
    private ArrayList<Branch> branchArrayList;

    public Bank(String name){
        setName(name);
        this.branchArrayList = new ArrayList<Branch>();
    }

    public boolean addBranch(String branchName){
        if (findBranch(branchName) == null){
            this.branchArrayList.add(new Branch(branchName));
            return true;
        }

        return false;
    }

    public boolean addCustomer(String branchName, String customerName, double initialAmount){
        Branch branch = findBranch(branchName);

        if (branch != null)
            return branch.newCustomer(customerName, initialAmount);

        return false;
    }

    public boolean addCustomerTransaction(String branchName, String customerName, double transactionAmount){
        Branch branch = findBranch(branchName);

        if (branch != null)
            return branch.addCustomerTransaction(customerName, transactionAmount);

        return false;
    }

    private Branch findBranch(String branchName){
        for (int i = 0; i < this.branchArrayList.size(); i++)
            if (this.branchArrayList.get(i).getName().equals(branchName))
                return this.branchArrayList.get(i);

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean listCustomer(String branchName, boolean showTransactions){
        Branch branch = findBranch(branchName);

        if (branch != null){
            System.out.println("Customer details for branch " + branch.getName());

                ArrayList<Customer> brunchCustomerArrayList = branch.getCustomerArrayList();

                for (int i = 0; i < brunchCustomerArrayList.size(); i++) {
                    System.out.println((i + 1) + ". Customer's name: " + brunchCustomerArrayList.get(i).getName());

                    if (showTransactions){
                        ArrayList<Double> transactionAmountArrayList = brunchCustomerArrayList.get(i).getTransactionAmountArrayList();

                        for (int j = 0; j < transactionAmountArrayList.size(); j++)
                            System.out.println((j + 1) + ". Amount: " + transactionAmountArrayList.get(j));
                    }
                }

                System.out.println();
                return true;
            }

        return false;
    }
}
