package com.ozeryavuzaslan;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("Denizli_Bank");
        bank.addBranch("KADIKOY");
        bank.addCustomer("KADIKOY", "Özer", 60.50);
        bank.addCustomer("KADIKOY", "Elif", 610.33);
        bank.addCustomer("KADIKOY", "Ahsen", 260.22);
        bank.addCustomer("KADIKOY", "Banu", 560.11);

        bank.addBranch("KARTAL");
        bank.addCustomer("KARTAL", "Murat", 6550.50);
        bank.addCustomer("KARTAL", "Sefer", 442.66);
        bank.addCustomer("KARTAL", "Nadire", 12.77);
        bank.addCustomer("KARTAL", "Aslı", 9.99);

        bank.listCustomer("KADIKOY", false);
        bank.listCustomer("KARTAL", false);

        bank.listCustomer("KADIKOY", true);
        bank.listCustomer("KARTAL", true);
    }
}