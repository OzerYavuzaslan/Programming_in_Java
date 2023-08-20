package com.ozeryavuzaslan.employeeservice.client;

import com.ozeryavuzaslan.employeeservice.dto.DepartmentDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DepartmentServiceClient {
    ResponseEntity<DepartmentDto> getDepartmentInfo(String employeeDepartmentCode, Map<String, String> headers);
    DepartmentDto getDepartmentInfoByUsingWebClient(String employeeDepartmentCode, Map<String, String> headers);
}
