package com.ozeryavuzaslan.employeeservice.dto.response;

import com.ozeryavuzaslan.employeeservice.dto.DepartmentDto;
import org.springframework.stereotype.Component;

@Component
public class HardCodedDepartmentInfoConverter {
    public DepartmentDto hardCodedDepartmentInfo(){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setCode("RD0001");
        departmentDto.setName("R&D Department");
        departmentDto.setDescription("Research and Development Department");

        return departmentDto;
    }
}
