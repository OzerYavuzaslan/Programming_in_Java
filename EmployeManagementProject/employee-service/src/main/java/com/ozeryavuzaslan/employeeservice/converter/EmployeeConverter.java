package com.ozeryavuzaslan.employeeservice.converter;

import com.ozeryavuzaslan.employeeservice.dto.request.EmployeeRequest;
import com.ozeryavuzaslan.employeeservice.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {
    public Employee convert(Employee employee, EmployeeRequest employeeRequest){
        employee.setName(employeeRequest.getName());
        employee.setSurname(employee.getSurname());
        employee.setDepartmentCode(employee.getDepartmentCode());
        employee.setBirthDate(employeeRequest.getBirthDate());
        employee.setStartDate(employeeRequest.getStartDate());
        employee.setEmail(employee.getEmail());

        return employee;
    }
}
