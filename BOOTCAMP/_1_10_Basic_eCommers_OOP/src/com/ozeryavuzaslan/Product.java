package com.ozeryavuzaslan;

public class Product {
    private String category;
    private String name;
    private double price;
    private int stock;
    private int cnt = 0;

    public Product(String category, String name, double price, int stock) {
        setCategory(category);
        setName(name);
        setPrice(price);
        setStock(stock);
        setCnt(getCnt() + 1);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
