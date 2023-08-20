package com.ozeryavuzaslan.departmentservice.util;

public final class Constants {
    public static final String DEPARTMENT_CODE_ENDPOINT_PATH = "/getByDepartmentCode/{code}";


    public static final String TOTAL_ERROR_DEFINITION = "total.error.message";
    public static final String FIRST_ERROR_DEFINITION = "first.error.message";
    public static final String DEPARTMENT_NOT_FOUND_DEFINITION = "department.not.found.message";
    public static final String ALREADY_IN_DEFINITION = "already.in.message";
    public static final String INVALID_DEPARTMENT_NAME_DEFINITION = "invalid.department.name.message";
    public static final String INVALID_DESCRIPTION_DEFINITION = "invalid.description.message";
    public static final String INVALID_CODE_DEFINITION = "invalid.code.message";


    public static final byte MIN_CHARACTER_SIZE = 3;
    public static final byte MIN_DEPARTMENT_CODE_CHARACTER_SIZE = 2;
    public static final byte MAX_CHARACTER_SIZE = 50;

    private Constants(){}
}
