package com.ozeryavuzaslan.departmentservice.service.implementation;

import com.ozeryavuzaslan.departmentservice.converter.DepartmentConverter;
import com.ozeryavuzaslan.departmentservice.dto.request.DepartmentRequest;
import com.ozeryavuzaslan.departmentservice.dto.response.DepartmentResponse;
import com.ozeryavuzaslan.departmentservice.model.Department;
import com.ozeryavuzaslan.departmentservice.repository.DepartmentRepository;
import com.ozeryavuzaslan.departmentservice.service.DepartmentFinderService;
import com.ozeryavuzaslan.departmentservice.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final DepartmentFinderService departmentFinderService;
    private final DepartmentConverter departmentConverter;

    @Override
    public DepartmentResponse saveDepartment(DepartmentRequest departmentRequest) {
        return modelMapper
                .map(departmentRepository
                        .save(modelMapper
                                .map(departmentRequest, Department.class)),
                        DepartmentResponse.class);
    }

    @Override
    public DepartmentResponse findOne(String departmentCode) {
        return modelMapper
                .map(departmentFinderService
                        .findSpecificDepartment(departmentCode),
                        DepartmentResponse.class);
    }

    @Override
    public DepartmentResponse findOne(long departmentID) {
        return modelMapper
                .map(departmentFinderService
                        .findSpecificDepartment(departmentID),
                        DepartmentResponse.class);
    }

    @Override
    public List<DepartmentResponse> findAll() {
        return departmentRepository
                .findAll()
                .stream()
                .map(department -> modelMapper.map(department, DepartmentResponse.class))
                .toList();
    }

    @Override
    public DepartmentResponse updateDepartment(String departmentCode, DepartmentRequest departmentRequest) {
        return modelMapper
                .map(departmentRepository
                        .save(departmentConverter
                                .convert(departmentFinderService
                                        .findSpecificDepartment(departmentCode),
                                        departmentRequest)),
                        DepartmentResponse.class);
    }

    @Override
    public DepartmentResponse updateDepartment(long departmentID, DepartmentRequest departmentRequest) {
        return modelMapper
                .map(departmentRepository
                                .save(departmentConverter
                                        .convert(departmentFinderService
                                                        .findSpecificDepartment(departmentID),
                                                departmentRequest)),
                        DepartmentResponse.class);
    }

    @Override
    public void deleteDepartment(long departmentID) {
        departmentRepository.deleteById(departmentFinderService.findSpecificDepartment(departmentID).getId());
    }

    @Override
    @Transactional
    public void deleteDepartment(String departmentCode) {
        departmentRepository.deleteByCode(departmentFinderService.findSpecificDepartment(departmentCode).getCode());
    }
}
