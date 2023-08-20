package com.ozeryavuzaslan.employeeservice.service;

import com.ozeryavuzaslan.employeeservice.dto.request.EmployeeRequest;
import com.ozeryavuzaslan.employeeservice.dto.response.EmployeeResponse;
import com.ozeryavuzaslan.employeeservice.dto.response.EmployeeWithDepartmentInfoResponse;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeResponse saveEmployee(EmployeeRequest departmentRequest, Map<String, String> headers);
    EmployeeWithDepartmentInfoResponse findOne(String employeeEmail, Map<String, String> headers);
    EmployeeWithDepartmentInfoResponse findOne(long employeeID, Map<String, String> headers);
    List<EmployeeResponse> findAll();
    List<EmployeeWithDepartmentInfoResponse> findAllWithFullDepartmentInfo(Map<String, String> headers);
    EmployeeResponse updateEmployee(String employeeEmail, EmployeeRequest employeeRequest);
    EmployeeResponse updateEmployee(long employeeID, EmployeeRequest employeeRequest);
    void deleteEmployee(long employeeID);
    void deleteEmployee(String  employeeEmail);
}
