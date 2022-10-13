package com.ozeryavuzaslan;

public class HealthyBurger extends Hamburger{
    public HealthyBurger() {
        super("Healthy Burger", "Brown Rye Bread Roll", "Soy based meat", 10);
    }

    @Override
    public void addAdditional(int choice) {
        super.addAdditional(choice);
        switch(choice) {
            case 5:
                double chickenBreastPrice = 4;
                this.setAdditionalPrice(this.getAdditionalPrice() + chickenBreastPrice);
                System.out.println("\nAdded: Chicken Breast");
                break;
            case 6:
                double ceaserSaladPrice = 5;
                this.setAdditionalPrice(this.getAdditionalPrice() + ceaserSaladPrice);
                System.out.println("\nAdded: Ceaser Salad");
                break;
        }
    }

    @Override
    public void checkOutOrder() {
        // TODO Auto-generated method stub
        super.checkOutOrder();
    }

    @Override
    public void printMenu() {
        super.printMenu();
        System.out.println("5. Chicken Breast || Base price: $4");
        System.out.println("6. Ceaser Salad   || Base price: $5");
    }
}