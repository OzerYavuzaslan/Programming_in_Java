package com.ozeryavuzaslan.departmentservice.service;

import com.ozeryavuzaslan.departmentservice.dto.request.DepartmentRequest;
import com.ozeryavuzaslan.departmentservice.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse saveDepartment(DepartmentRequest departmentRequest);
    DepartmentResponse findOne(String departmentCode);
    DepartmentResponse findOne(long departmentID);
    List<DepartmentResponse> findAll();
    DepartmentResponse updateDepartment(String departmentCode, DepartmentRequest departmentRequest);
    DepartmentResponse updateDepartment(long departmentID, DepartmentRequest departmentRequest);
    void deleteDepartment(long departmentID);
    void deleteDepartment(String  departmentCode);
}
