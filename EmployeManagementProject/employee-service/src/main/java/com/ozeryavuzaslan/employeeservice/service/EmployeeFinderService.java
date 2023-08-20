package com.ozeryavuzaslan.employeeservice.service;

import com.ozeryavuzaslan.employeeservice.model.Employee;

public interface EmployeeFinderService {
    Employee findSpecificEmployee(long employeeID);
    Employee findSpecificEmployee(String employeeEmail);
}
