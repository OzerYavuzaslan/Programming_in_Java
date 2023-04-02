package com.ozeryavuzaslan.webservices.restfulwebservicesProject.util;

public final class Constants {
    public static final String USER_NOT_FOUND = " (User) not found in the system!";
    public static final String PSQL_EXCEPTION_CODE = "23505";
    public static final byte MIN_CHARACTER_SIZE = 3;
    public static final byte MAX_CHARACTER_SIZE = 50;
    public static final String NOT_FOUND_DEFINITION = "not.found.message";
    public static final String ALREADY_IN_DEFINITION = "already.in.message";
    public static final String INVALID_EMAIL_DEFINITION = "invalid.email.message";
    public static final String INVALID_NAME_SURNAME_DEFINITION = "invalid.name.surname.message";
    public static final String INVALID_BIRTHDATE_DEFINITION = "invalid.birthdate.message";
    public static final String TOTAL_ERROR_DEFINITION = "total.error.message";
    public static final String FIRST_ERROR_DEFINITION = "first.error.message";
    public static final String GOOD_MORNING_DEFINITION = "good.morning.message";
    public static final String DEFAULT_MSG_DEFINITION = "Default Message";
    public static final String ALL_USERS = "all-users";
    public static final String SPECIFIC_USER = "specific-user";
    public static final String USER_EMAIL_PATH = "/getuserbyemail/{eMail}";
    public static final String TITLE_DEFINITION = "title.error.message";
    public static final String POST_DEFINITION = "post.error.message";
    public static final String POST_ORIGIN_PATH = "/posts";
    public static final String POST_TITLE_PATH = "/byTitle/{title}";
    public static final String POST_NOT_FOUND_DEFINITION = "post.not.found.error.message";
    public static final String ALL_POSTS = "all-posts";

    private Constants(){
    }
}
