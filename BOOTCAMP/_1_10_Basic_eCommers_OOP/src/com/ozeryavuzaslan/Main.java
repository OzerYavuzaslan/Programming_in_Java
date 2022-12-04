package com.ozeryavuzaslan;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Random randomIdGenerator = new Random();

        Customer cem = new Customer(randomIdGenerator.generateRandomValue(), "Cem", 27);
        String  cemOrderId = randomIdGenerator.generateRandomValue();
        cem.addOrder(cemOrderId);
        cem.addProduct(cemOrderId, "Smart Phone", "Samsung", 1000.0D, 10);
        cem.addProduct(cemOrderId, "TV", "Sony\t", 2000.0D, 20);
        cem.addProduct(cemOrderId, "Laptop", "Toshiba", 2877.66D, 30);
        cem.printOrderList(cemOrderId);
        cem.findProductTotalNumberOfACustomer(cemOrderId);
        String cemInvoiceID = randomIdGenerator.generateRandomValue();
        cem.addInvoice(cemInvoiceID, cemOrderId, "Cem");
        System.out.println("------------------------------------------------");

        Customer cem2 = new Customer(randomIdGenerator.generateRandomValue(), "cem", 26);
        String  cemOrderId2 = randomIdGenerator.generateRandomValue();
        cem2.addOrder(cemOrderId2);
        cem2.addProduct(cemOrderId2, "Smart Phone", "Iphone", 10000.0D, 31);
        cem2.addProduct(cemOrderId2, "TV", "Samsung", 4000.0D, 20);
        cem2.addProduct(cemOrderId2, "Laptop", "Acer", 1423.123D, 10);
        cem2.printOrderList(cemOrderId2);
        cem2.findProductTotalNumberOfACustomer(cemOrderId2);
        String cemInvoiceID2 = randomIdGenerator.generateRandomValue();
        cem2.addInvoice(cemInvoiceID2, cemOrderId2, "Cem");
        System.out.println("------------------------------------------------");

        Customer cem3 = new Customer(randomIdGenerator.generateRandomValue(), "cem", 18);
        String  cemOrderId3 = randomIdGenerator.generateRandomValue();
        cem3.addOrder(cemOrderId3);
        cem3.addProduct(cemOrderId3, "T-Shirt", "LCW", 222.0D, 31);
        cem3.addProduct(cemOrderId3, "Shoe", "Nike", 500.0D, 20);
        cem3.addProduct(cemOrderId3, "Pant", "Mavi", 444.0D, 10);
        cem3.addProduct(cemOrderId3, "Socks", "Beymen", 111.88D, 10);
        cem3.printOrderList(cemOrderId3);
        cem3.findProductTotalNumberOfACustomer(cemOrderId3);
        String cemInvoiceID3 = randomIdGenerator.generateRandomValue();
        cem3.addInvoice(cemInvoiceID3, cemOrderId3, "Cem");
        System.out.println("------------------------------------------------");

        Customer ozer = new Customer(randomIdGenerator.generateRandomValue(), "Özer", 34);
        String  ozerOrderID = randomIdGenerator.generateRandomValue();
        ozer.addOrder(ozerOrderID);
        ozer.addProduct(ozerOrderID, "E-Drum", "Roland", 15000.0D, 31);
        ozer.addProduct(ozerOrderID, "PC", "Hardware", 40000.0D, 10);
        ozer.printOrderList(ozerOrderID);
        ozer.findProductTotalNumberOfACustomer(ozerOrderID);
        String ozerInvoiceId = randomIdGenerator.generateRandomValue();
        ozer.addInvoice(ozerInvoiceId, ozerOrderID, "Özer");
        System.out.println("------------------------------------------------");

        Customer bayram = new Customer(randomIdGenerator.generateRandomValue(), "Bayram", 34);
        String  bayramOrderID = randomIdGenerator.generateRandomValue();
        bayram.addOrder(bayramOrderID);
        bayram.addProduct(bayramOrderID, "Cigar", "Winston", 30.33D, 31);
        bayram.addProduct(bayramOrderID, "Laptop", "Casper", 1234.123D, 10);
        bayram.printOrderList(bayramOrderID);
        bayram.findProductTotalNumberOfACustomer(bayramOrderID);
        String bayramInvoiceId = randomIdGenerator.generateRandomValue();
        bayram.addInvoice(bayramInvoiceId, bayramOrderID, "Bayram");
        System.out.println("------------------------------------------------");

        ArrayList<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(ozer);
        customerArrayList.add(bayram);
        customerArrayList.add(cem);
        customerArrayList.add(cem2);
        customerArrayList.add(cem3);
        System.out.println("\nTotal price of all Cem's orders: " + Customer.printConditionalInvoiceList(customerArrayList, "Cem", 25, 30));
        Customer.getCustomersForSpecificPayment(1500.0D, customerArrayList);
        System.out.println("Total Customer: " + Customer.getTotalCustomer());
        System.out.println("------------------------------------------------");
    }
}
