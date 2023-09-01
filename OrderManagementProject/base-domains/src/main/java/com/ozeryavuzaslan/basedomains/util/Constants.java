package com.ozeryavuzaslan.basedomains.util;

public final class Constants {
    public static final String STOCK_GET_ENDPOINT = "/getByProductId/{id}";
    public static final String CATEGORY_GET_ENDPOINT = "/getByCategoryId/{id}";
    public static final String TOTAL_ERRORS = "Total Error(s): ";
    public static final String FIRST_ERROR = "First Error: ";
    public static final String STOCK_NOT_FOUND = "The requested stock is not found! Consider add new stock.";
    public static final String CATEGORY_NOT_FOUND = "The requested category is not found! Consider add new category.";
    public static final String QUANTITY_AMOUNT_NOT_ENOUGH = "There is no enough stock to take the requested amount of the product!";
    public static final String ALREADY_IN_DEFINITION = "The category is already in the system!";

    private Constants(){
    }
}