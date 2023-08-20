package com.ozeryavuzaslan.departmentservice.service.implementation;

import com.ozeryavuzaslan.departmentservice.exception.DepartmentNotFoundException;
import com.ozeryavuzaslan.departmentservice.model.Department;
import com.ozeryavuzaslan.departmentservice.repository.DepartmentRepository;
import com.ozeryavuzaslan.departmentservice.service.DepartmentFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ozeryavuzaslan.departmentservice.util.Constants.DEPARTMENT_NOT_FOUND_DEFINITION;

@Service
@RequiredArgsConstructor
public class DepartmentFinderServiceImp implements DepartmentFinderService {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department findSpecificDepartment(long departmentID) {
        return departmentRepository
                .findById(departmentID)
                .orElseThrow(
                        () -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND_DEFINITION)
                );
    }

    @Override
    public Department findSpecificDepartment(String departmentCode) {
        return departmentRepository
                .findByCode(departmentCode)
                .orElseThrow(
                        () -> new DepartmentNotFoundException(DEPARTMENT_NOT_FOUND_DEFINITION)
                );
    }
}
