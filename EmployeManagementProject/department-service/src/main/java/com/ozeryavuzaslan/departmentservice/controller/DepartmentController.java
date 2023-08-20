package com.ozeryavuzaslan.departmentservice.controller;

import com.ozeryavuzaslan.departmentservice.dto.request.DepartmentRequest;
import com.ozeryavuzaslan.departmentservice.dto.response.DepartmentResponse;
import com.ozeryavuzaslan.departmentservice.service.DepartmentService;
import com.ozeryavuzaslan.departmentservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ozeryavuzaslan.departmentservice.util.Constants.DEPARTMENT_CODE_ENDPOINT_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final CustomLocation customLocation;

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest){
        return ResponseEntity
                .created(customLocation
                        .getURILocation(DEPARTMENT_CODE_ENDPOINT_PATH,
                                departmentService
                                        .saveDepartment(departmentRequest)
                                        .getCode()))
                .build();
    }

    @GetMapping("/getByDepartmentCode/{code}")
    public ResponseEntity<DepartmentResponse> retrieveDepartmentByCode(@PathVariable String code){
        return ResponseEntity.ok(departmentService.findOne(code));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> retrieveDepartmentByID(@PathVariable long id){
        return ResponseEntity.ok(departmentService.findOne(id));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> retrieveAllDepartments(){
        return ResponseEntity.ok(departmentService.findAll());
    }

    @PutMapping
    public ResponseEntity<DepartmentResponse> updateDepartment(@RequestParam String code,
                                                               @RequestBody DepartmentRequest departmentRequest){
        return ResponseEntity.ok(departmentService.updateDepartment(code, departmentRequest));
    }

    @PutMapping("/updateByID")
    public ResponseEntity<DepartmentResponse> updateDepartment(@RequestParam long ID,
                                                               @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(departmentService.updateDepartment(ID, departmentRequest));
    }

    @DeleteMapping
    public void deleteDepartment(@RequestParam String code){
        departmentService.deleteDepartment(code);
    }

    @DeleteMapping("deleteByID")
    public void deleteDepartment(@RequestParam long ID){
        departmentService.deleteDepartment(ID);
    }
}
