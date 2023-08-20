package com.ozeryavuzaslan.departmentservice.converter;

import com.ozeryavuzaslan.departmentservice.dto.request.DepartmentRequest;
import com.ozeryavuzaslan.departmentservice.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter {
    public Department convert(Department department, DepartmentRequest departmentRequest){
        department.setCode(departmentRequest.getCode());
        department.setDescription(department.getDescription());
        department.setName(departmentRequest.getName());

        return department;
    }
}
