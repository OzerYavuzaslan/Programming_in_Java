package com.ozeryavuzaslan;

public class DeluxeBurger extends Hamburger{
    public DeluxeBurger() {
        super("Deluxe Burger", "Truffle Bread", "Kobe Beef", 30);
    }

    @Override
    public void addAdditional(int choice) {
        switch(choice) {
            case 1:
                double chipsPrice = 7;
                this.setAdditionalPrice(this.getAdditionalPrice() + chipsPrice);
                System.out.println("\nAdded: Chips");
                break;
            case 2:
                double cokePrice = 4;
                this.setAdditionalPrice(this.getAdditionalPrice() + cokePrice);
                System.out.println("\nAdded: Coke");
                break;
        }
    }

    @Override
    public void checkOutOrder() {
        super.checkOutOrder();
    }

    @Override
    public void printMenu() {
        System.out.println("======== BILLS BURGER MENU =======");
        System.out.println("1. Chips	  || Base price: $1");
        System.out.println("2. Coke 	  || Base price: $1");
    }
}
