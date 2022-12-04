package com.ozeryavuzaslan;

public class Invoice {
    private String Id;
    private String orderId;
    private String customerName;
    private double totalPrice;

    public Invoice(String id, String orderId, String customerName, double totalPrice) {
        setId(id);
        setOrderId(orderId);
        setCustomerName(customerName);
        setTotalPrice(totalPrice);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
