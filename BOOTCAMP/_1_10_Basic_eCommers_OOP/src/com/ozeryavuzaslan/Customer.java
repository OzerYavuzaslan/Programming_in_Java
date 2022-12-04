package com.ozeryavuzaslan;

import java.util.ArrayList;

public class Customer {
    private String name;
    private String id;
    private int age;
    private static int totalCustomer = 0;
    private ArrayList<Order> ordersList;

    public Customer(String id, String name, int age) {
        setId(id);
        setName(name);
        setAge(age);
        setOrdersList(new ArrayList<>());
        setTotalCustomer(getTotalCustomer() + 1);
    }

    private Order findOrder(String orderID){
        for (int i = 0; i < getOrdersList().size(); i++){
            if (getOrdersList().get(i).getOrderId().equalsIgnoreCase(orderID))
                return getOrdersList().get(i);
        }

        return null;
    }

    public void addOrder(String orderID){
        if (findOrder(orderID) == null){
            getOrdersList().add(new Order(orderID));
            System.out.println("Order is taken with " + orderID + " (Order ID).");
        }
        else
            System.out.println("Order is already in the system. Order ID: " + orderID);
    }

    public void addProduct(String orderID, String productCategory, String productName, double productPrice, int productStock){
        Order order = findOrder(orderID);

        if (order != null)
            order.addNewProduct(productCategory, productName, productPrice, productStock);
    }

    private double getTotalOrderPrice(String orderID){
        Order order = findOrder(orderID);
        double totalPrice = 0;

        if (order != null)
            for (int i = 0; i < order.getProductList().size(); i++)
                totalPrice += order.getProductList().get(i).getPrice();

        return totalPrice;
    }

    public void addInvoice(String invoiceID, String orderID, String customerName){
        Order order = findOrder(orderID);

        if (order != null)
            order.addNewInvoice(invoiceID, orderID, customerName, getTotalOrderPrice(orderID));
    }

    public void printOrderList(String orderID){
        Order order = findOrder(orderID);

        if (order != null){
            System.out.println("\n-----" + getName() + "'s order list-----");
            System.out.println("Product Name | Product Price");
            ArrayList<Product> orderProductList = order.getProductList();

            for (Product product : orderProductList)
                System.out.println(product.getName() + " \t " + product.getPrice() + "\t");
        }
    }

    public static double printConditionalInvoiceList(ArrayList<Customer> customerArrayList, String customerName, int startAge, int endAge){
        double totalPrice = 0;

        for (int i = 0; i < customerArrayList.size() && customerArrayList.size() > 0; i++){
            if (customerArrayList.get(i).getAge() > startAge &&
                customerArrayList.get(i).getAge() < endAge &&
                customerArrayList.get(i).getName().equalsIgnoreCase(customerName)) {

                ArrayList<Order> tmpOrderList = customerArrayList.get(i).getOrdersList();

                for (int j = 0; j < tmpOrderList.size() && tmpOrderList.size() > 0; j++) {
                    ArrayList<Invoice> tmpInvoiceList = tmpOrderList.get(j).getInvoiceList();

                    for (int k = 0; k < tmpInvoiceList.size() && tmpInvoiceList.size() > 0; k++) {
                        totalPrice += tmpInvoiceList.get(k).getTotalPrice();
                    }
                }
            }
        }

        return totalPrice;
    }

    public static void getCustomersForSpecificPayment(double paymentThreshold, ArrayList<Customer> customerArrayList){
        System.out.println("-----Persons who have paid more than " + paymentThreshold + "-----");

        for (int i = 0; i < customerArrayList.size() && customerArrayList.size() > 0; i++){
            ArrayList<Order> tmpOrderList = customerArrayList.get(i).getOrdersList();

            for (int j = 0; j < tmpOrderList.size() && tmpOrderList.size() > 0; j++){
                ArrayList<Invoice> tmpInvoiceList = tmpOrderList.get(j).getInvoiceList();

                for (int k = 0; k < tmpInvoiceList.size() && tmpInvoiceList.size() > 0; k++){
                    if (tmpInvoiceList.get(k).getTotalPrice() > paymentThreshold)
                        System.out.println("\t\t\t\t" + tmpInvoiceList.get(k).getCustomerName());
                }
            }
        }
    }

    private int getCustomersTotalProductCount(Order order){
        int tmpTotal = 0;

        if (order != null)
            for (int i = 0; i < order.getProductList().size(); i++)
                tmpTotal += order.getProductList().get(i).getCnt();

        return tmpTotal;
    }

    public void findProductTotalNumberOfACustomer(String orderID){
        Order order = findOrder(orderID);

        if(order != null){
            System.out.println("\n" + getName() + "'s total product count is: " + getCustomersTotalProductCount(order));
            System.out.println(getName() + "'s total payment is: " + getTotalOrderPrice(orderID));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static int getTotalCustomer() {
        return totalCustomer;
    }

    public static void setTotalCustomer(int totalCustomer) {
        Customer.totalCustomer = totalCustomer;
    }

    public ArrayList<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(ArrayList<Order> ordersList) {
        this.ordersList = ordersList;
    }
}
