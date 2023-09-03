package com.ozeryavuzaslan.basedomains.util;

public final class Constants {
    public final static String UUID_VALID_NULL_MSG = "UUID cannot be null!";
    public final static String PRODUCT_NAME_NULL_MSG = "Product name cannot be null!";
    public final static String PRODUCT_NAME_BLANK_MSG = "Product name cannot be blank!";
    public final static String PRODUCT_NAME_EMPTY_MSG = "Product name cannot be empty!";
    public final static int PRODUCT_NAME_MIN_SIZE = 3;
    public final static int PRODUCT_NAME_MAX_SIZE = 25;
    public final static String PRODUCT_NAME_SIZE_MSG = "Product name characters must be in between 3 and 25";
    public final static String CATEGORY_NULL_MSG = "Category object cannot be null!";
    public final static String QUANTITY_NEGATIVE_MSG = "Quantity cannot be negative and zero!";
    public final static String PRICE_NEGATIVE_MSG = "Price cannot be negative and zero!";
    public final static String PRICE_OR_QUANTITY_NULL_MSG = "Price or quantity cannot be null!";


    private Constants(){
    }
}