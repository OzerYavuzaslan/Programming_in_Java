package com.ozeryavuzaslan;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private ArrayList<Invoice> invoiceList;
    private ArrayList<Product> productList;

    public Order(String orderId) {
        setOrderId(orderId);
        setProductList(new ArrayList<>());
        setInvoiceList(new ArrayList<>());
    }

    public void addNewProduct(String productCategory, String productName, double productPrice, int productStock){
        getProductList().add(new Product(productCategory, productName, productPrice, productStock));
        System.out.println(productName + " (product) has been added.");
    }

    public void addNewInvoice(String invoiceID, String orderID, String customerName, double totalOrderPrice){
        getInvoiceList().add(new Invoice(invoiceID, orderID, customerName, totalOrderPrice));
        System.out.println(orderID + "'s invoice has been created (invoice ID: " + invoiceID + ")");
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public ArrayList<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(ArrayList<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }
}
