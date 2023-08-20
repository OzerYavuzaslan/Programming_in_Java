package com.ozeryavuzaslan.departmentservice.service;

import com.ozeryavuzaslan.departmentservice.model.Department;

public interface DepartmentFinderService {
    Department findSpecificDepartment(long departmentID);
    Department findSpecificDepartment(String departmentCode);
}
