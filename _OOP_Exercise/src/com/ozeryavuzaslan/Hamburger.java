package com.ozeryavuzaslan;

public class Hamburger {
    private String name;
    private String breadRollType;
    private String meat;
    private double basePrice;
    private double additionalPrice;

    public Hamburger(String name, String breadRollType, String meat, double price) {
        this.name = name;
        this.breadRollType = breadRollType;
        this.meat = meat;
        this.basePrice = price;
    }

    public void printMenu() {
        System.out.println("======== BILLS BURGER MENU =======");
        System.out.println("1. Lettuce 	  || Base price: $3");
        System.out.println("2. Tomato  	  || Base price: $2");
        System.out.println("3. Cheese  	  || Base price: $3");
        System.out.println("4. Onions	  || Base price: $1");
    }

    public void addAdditional(int choice) {
        if(choice > 0 && choice < 5 ) {
            switch(choice) {
                case 1: //letuce
                    double lettucePrice = 3;
                    this.setAdditionalPrice(this.getAdditionalPrice() + lettucePrice);
                    System.out.println("\nAdded: Lettuce");
                    break;
                case 2:
                    double tomatoPrice = 2;
                    this.setAdditionalPrice(this.getAdditionalPrice() + tomatoPrice);
                    System.out.println("\nAdded: Tomato");
                    break;
                case 3:
                    double cheesePrice = 3;
                    this.setAdditionalPrice(this.getAdditionalPrice() + cheesePrice);
                    System.out.println("\nAdded: Cheese");
                    break;
                case 4:
                    double onionPrice = 1;
                    this.setAdditionalPrice(this.getAdditionalPrice() + onionPrice);
                    System.out.println("\nAdded: Onion");
                    break;
            }
        }
    }

    public void checkOutOrder() {
        System.out.println("\n==================== CHECK OUT ORDER ====================");
        System.out.println("Burger Type: " + getName() + "		Price: $ " + getBasePrice());
        System.out.println("Additions: " + 			     "				Price: $ " + getAdditionalPrice());
        System.out.println("----------------------------------------TOTAL: $ " + getTotalPrice());
        System.out.println("");
    }

    public String getName() {
        return name;
    }

    public String getBreadRollType() {
        return breadRollType;
    }

    public String getMeat() {
        return meat;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getAdditionalPrice() {
        return additionalPrice;
    }

    public double getTotalPrice() {
        return this.basePrice + this.getAdditionalPrice();
    }

    public void setAdditionalPrice(double additionalPrice) {
        this.additionalPrice = additionalPrice;
    }
}