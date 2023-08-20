package com.ozeryavuzaslan.organizationservice.util;

public final class Constants {
    public static final String INVALID_START_DATE_DEFINITION = "invalid.start.date.message";
    public static final String INVALID_CODE_DEFINITION = "invalid.code.message";
    public static final String INVALID_NAME_DEFINITION = "invalid.name.message";
    public static final String ALREADY_IN_DEFINITION = "already.in.message";
    public static final String ORGANIZATION_NOT_FOUND_DEFINITION = "organization.not.found.message";
    public static final String TOTAL_ERROR_DEFINITION = "total.error.message";
    public static final String FIRST_ERROR_DEFINITION = "first.error.message";
    public static final byte MIN_CHARACTER_SIZE = 3;
    public static final byte MIN_ORGANIZATION_CODE_CHARACTER_SIZE = 2;
    public static final byte MAX_CHARACTER_SIZE = 50;
    public static final String ORGANIZATION_CODE_ENDPOINT = "/getByOrganizationCode/{code}";

    private Constants(){
    }
}
