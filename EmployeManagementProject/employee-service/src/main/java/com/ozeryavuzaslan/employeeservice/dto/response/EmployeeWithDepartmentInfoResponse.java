package com.ozeryavuzaslan.employeeservice.dto.response;

import com.ozeryavuzaslan.employeeservice.dto.DepartmentDto;
import com.ozeryavuzaslan.employeeservice.dto.OrganizationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithDepartmentInfoResponse {
    private EmployeeResponse employee;
    private DepartmentDto department;
    private OrganizationDto organization;
}