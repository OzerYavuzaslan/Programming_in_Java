package com.ozeryavuzaslan;

public class Main {
    public static void main(String[] args) {
        Hamburger hamburger = new Hamburger("Base Burger","Italian Bread","Beef Patty",15);
        hamburger.printMenu();
        hamburger.addAdditional(2);
        hamburger.addAdditional(3);
        hamburger.addAdditional(1);
        hamburger.addAdditional(4);
        hamburger.checkOutOrder();

        HealthyBurger healthyBurger = new HealthyBurger();
        healthyBurger.printMenu();
        healthyBurger.addAdditional(5);
        healthyBurger.addAdditional(6);
        healthyBurger.addAdditional(1);
        healthyBurger.addAdditional(2);
        healthyBurger.checkOutOrder();

        DeluxeBurger deluxeBurger = new DeluxeBurger();
        deluxeBurger.printMenu();
        deluxeBurger.addAdditional(1);
        deluxeBurger.addAdditional(1);
        deluxeBurger.addAdditional(2);
        deluxeBurger.checkOutOrder();
    }
}