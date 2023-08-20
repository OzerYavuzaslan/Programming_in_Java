package com.ozeryavuzaslan.employeeservice.service.implementation;

import com.ozeryavuzaslan.employeeservice.exception.EmployeeNotFoundException;
import com.ozeryavuzaslan.employeeservice.model.Employee;
import com.ozeryavuzaslan.employeeservice.repository.EmployeeRepository;
import com.ozeryavuzaslan.employeeservice.service.EmployeeFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ozeryavuzaslan.employeeservice.util.Constants.EMPLOYEE_NOT_FOUND_DEFINITION;

@Service
@RequiredArgsConstructor
public class EmployeeFinderServiceImp implements EmployeeFinderService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findSpecificEmployee(long employeeID) {
        return employeeRepository
                .findById(employeeID)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_DEFINITION)
                );
    }

    @Override
    public Employee findSpecificEmployee(String employeeEmail) {
        return employeeRepository
                .findByEmail(employeeEmail)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_DEFINITION)
                );
    }
}
