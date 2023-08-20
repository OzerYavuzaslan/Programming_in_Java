package com.ozeryavuzaslan.employeeservice.util;

public final class Constants {
    public static final String BASE_DEPARTMENT_SERVICE_URL = "http://localhost:8080";
    public static final String DEPARTMENT_REQUEST_MAPPING = "/departments";
    public static final String DEPARTMENT_GET_BY_CODE_ENDPOINT = "/getByDepartmentCode";
    public static final String EMPLOYEE_EMAIL_ENDPOINT = "/getByEmployeeEmail/{email}";
    public static final String ORGANIZATION_REQUEST_MAPPING_BY_CODE = "/organizations/getByOrganizationCode/";


    public static final String TOTAL_ERROR_DEFINITION = "total.error.message";
    public static final String FIRST_ERROR_DEFINITION = "first.error.message";
    public static final String EMPLOYEE_NOT_FOUND_DEFINITION = "employee.not.found.message";
    public static final String ALREADY_IN_DEFINITION = "already.in.message";
    public static final String INVALID_NAME_SURNAME_DEFINITION = "invalid.name.surname.message";
    public static final String INVALID_EMAIL_DEFINITION = "invalid.email.message";
    public static final String INVALID_CODE_DEFINITION = "invalid.code.message";
    public static final String INVALID_BIRTHDATE_DEFINITION = "invalid.birthdate.message";
    public static final String INVALID_START_DATE_DEFINITION = "invalid.start.date.message";


    public static final byte MIN_CHARACTER_SIZE = 3;
    public static final byte MIN_DEPARTMENT_CODE_CHARACTER_SIZE = 2;
    public static final byte MAX_CHARACTER_SIZE = 50;

    private Constants(){
    }
}
