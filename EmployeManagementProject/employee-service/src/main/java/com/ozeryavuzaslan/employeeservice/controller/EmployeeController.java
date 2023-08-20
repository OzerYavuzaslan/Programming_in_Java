package com.ozeryavuzaslan.employeeservice.controller;

import com.ozeryavuzaslan.employeeservice.dto.request.EmployeeRequest;
import com.ozeryavuzaslan.employeeservice.dto.response.EmployeeResponse;
import com.ozeryavuzaslan.employeeservice.dto.response.EmployeeWithDepartmentInfoResponse;
import com.ozeryavuzaslan.employeeservice.service.EmployeeService;
import com.ozeryavuzaslan.employeeservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.ozeryavuzaslan.employeeservice.util.Constants.EMPLOYEE_EMAIL_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CustomLocation customLocation;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest,
                                                           @RequestHeader Map<String, String> headers){
        return ResponseEntity
                .created(customLocation
                        .getURILocation(EMPLOYEE_EMAIL_ENDPOINT,
                                employeeService
                                        .saveEmployee(employeeRequest, headers)
                                        .getEmail()))
                .build();
    }

    @GetMapping("/getByEmployeeEmail/{email}")
    public ResponseEntity<EmployeeWithDepartmentInfoResponse> retrieveEmployeeEmail(@PathVariable String email,
                                                                                    @RequestHeader Map<String, String> headers){
        return ResponseEntity.ok(employeeService.findOne(email, headers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeWithDepartmentInfoResponse> retrieveEmployeeByID(@PathVariable long id,
                                                                                   @RequestHeader Map<String, String> headers){
        return ResponseEntity.ok(employeeService.findOne(id, headers));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> retrieveAllEmployees(){
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/getEmployeesWithFullDepartmentInfo")
    public ResponseEntity<List<EmployeeWithDepartmentInfoResponse>> retrieveAllEmployeesWithFullDepartmentInfo(@RequestHeader Map<String, String> headers){
        return ResponseEntity.ok(employeeService.findAllWithFullDepartmentInfo(headers));
    }

    @PutMapping
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestParam String eMail,
                                                           @RequestBody EmployeeRequest employeeRequest){
        return ResponseEntity.ok(employeeService.updateEmployee(eMail, employeeRequest));
    }

    @PutMapping("/updateByID")
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestParam long ID,
                                                           @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(ID, employeeRequest));
    }

    @DeleteMapping
    public void deleteEmployee(@RequestParam String email){
        employeeService.deleteEmployee(email);
    }

    @DeleteMapping("deleteByID")
    public void deleteEmployee(@RequestParam long ID){
        employeeService.deleteEmployee(ID);
    }
}