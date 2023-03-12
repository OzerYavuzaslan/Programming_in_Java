package com.biletimcepte.util;

import lombok.Getter;

@Getter
public final class Constants {
    public static final String USER_NOT_FOUND_OR_ADMIN = " is not ADMIN or not specified as an ADMIN in the system!";
    public static final String USER_ALREADY_IN_THE_SYSTEM = "Email of that user is already in the system! Use different email to register.";
    public static final String VOYAGE_NOT_FOUND = " related voyage is not found!";
    public static final String LOGIN_SUCCESSFUL = "Successfully logged in";
    public static final String REGISTER_SUCCESSFUL = "User has been successfully registered";
    public static final String LOGIN_NOT_SUCCESSFUL = "Email or password is incorrect";
    public static final String REGISTER_NOT_SUCCESSFUL = "This email is already in the system. Please use different email than this:  ";
    public static final String USER_FOUND_AND_UPDATED = "User is found, and updated successfully.";
    public static final String USER_NOT_FOUND = "User is not found!";
    public static final String BOOKING_NOT_FOUND = "Booking is not found!";
    public static final String NOT_ENOUGH_SEATS_FOUND = "There is no available seats!";
    public static final String NO_TICKET_INFO_FOUND = "There is no ticket info in the booking list. Thus cannot proceed booking process!";
    public static final int LIMITED_MAX_TICKET_NUM_FOR_INDIVIDUAL_PASSENGER = 5;
    public static final int MAX_MALE_PASSENGERS_TO_BUY_TICKETS_PER_BOOKING  = 2;
    public static final int LIMITED_MAX_TICKET_NUM_FOR_CORPORATE_PASSENGER = 20;
    public static final String TICKET = " tickets!";
    public static final String LIMITED_MAX_TICKET_FOR_INDIVIDUALS = "Individual users can buy at MAX ";
    public static final String LIMITED_MAX_MALE_PASSENGERS = "Individual male users can buy at MAX ";
    public static final String LIMITED_MAX_TICKET_FOR_CORPORATE = "CORPORATE users can buy at MAX ";
    public static final String BOOKING_PAYMENT_ALREADY_DONE = "Passenger has already paid the payment!";
    public static final String PAYMENT_SUCCESSFUL = "The payment is successfully done. Have a safe journey: ";
    public static final String ROLE_NOT_FOUND = "User role not found!";
    public static final String BAD_CREDENTIAL = "Invalid username and password!";
    public static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";

    private Constants(){
    }
}
