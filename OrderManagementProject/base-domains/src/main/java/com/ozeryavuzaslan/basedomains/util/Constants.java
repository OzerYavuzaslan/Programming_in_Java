package com.ozeryavuzaslan.basedomains.util;

public final class Constants {
    public final static String UUID_VALID_NULL_MSG = "UUID cannot be null!";
    public final static String PRODUCT_NAME_NULL_MSG = "Product name cannot be null!";
    public final static String PRODUCT_NAME_BLANK_MSG = "Product name cannot be blank!";
    public final static String PRODUCT_NAME_EMPTY_MSG = "Product name cannot be empty!";
    public final static String PRODUCT_NAME_SIZE_MSG = "Product name characters must be in between 3 and 25";
    public final static int NAME_MIN_SIZE = 3;
    public final static int NAME_MAX_SIZE = 25;
    public final static String CATEGORY_NULL_MSG = "Category object cannot be null!";
    public final static String QUANTITY_NEGATIVE_MSG = "Quantity cannot be negative and zero!";
    public final static String PRICE_NEGATIVE_MSG = "Price cannot be negative and zero!";
    public final static String PRICE_OR_QUANTITY_NULL_MSG = "Price or quantity cannot be null!";
    public final static String CATEGORY_NAME_NULL_MSG = "Category name cannot be null!";
    public final static String CATEGORY_NAME_BLANK_MSG = "Category name cannot be blank!";
    public final static String CATEGORY_NAME_EMPTY_MSG = "Category name cannot be empty!";
    public final static String CATEGORY_NAME_SIZE_MSG = "Category name characters must be in between 3 and 25";
    public final static String EMAIL_NOT_VALID = "Given email is not valid!";
    public final static String BRAND_NAME_NOT_VALID = "Brand name can't be null, empty and blank! Brand name characters must be in between 3 and 25";
    public final static String PERSON_NAME_NOT_VALID = "Name and surname characters must be in between 3 and 25";
    public final static String NAME_SURNAME_NOT_VALID = "Name and surname can't be null, blank and empty!";
    public final static String PHONE_NUMBER_NOT_VALID = "Phone number can't be null, blank and empty!";
    public final static String ORDER_ID_NOT_VALID = "OrderId must be positive and can't be null!";
    public final static String PAYMENT_AMOUNT_INFO_NOT_VALID = "Tax rate, total price and total price without tax info must be positive! They also can't be null";
    public final static String REFUND_AMOUNT_INFO_NOT_VALID = "Refund amount can't be null, negative and zero!";
    public final static String CURRENCY_VALIDATION_LIST = "USD|EUR|TL";
    public final static String MONETARY_UNIT_VALIDATION_LIST = "CENT|KURUS";
    public final static String PAYMENT_PROVIDER_TYPE_VALIDATION_LIST = "STRIPE|PAYPAL|CREDIT_CARD";
    public final static String CURRENCY_NOT_VALID = "Currency is not valid!";
    public final static String MONETARY_UNIT_NOT_VALID = "Monetary unit is not valid!";
    public final static String PAYMENT_PROVIDER_NOT_VALID = "Payment provider is not valid!";
    public final static String DISCOUNT_NEGATIVE_MSG = "Discount cannot be negative!";

    private Constants(){
    }
}