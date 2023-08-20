package com.ozeryavuzaslan.employeeservice.service.implementation;

import com.ozeryavuzaslan.employeeservice.client.DepartmentServiceClient;
import com.ozeryavuzaslan.employeeservice.client.DepartmentServiceOpenFeignClient;
import com.ozeryavuzaslan.employeeservice.client.OrganizationServiceOpenFeignClient;
import com.ozeryavuzaslan.employeeservice.converter.EmployeeConverter;
import com.ozeryavuzaslan.employeeservice.dto.response.HardCodedDepartmentInfoConverter;
import com.ozeryavuzaslan.employeeservice.dto.DepartmentDto;
import com.ozeryavuzaslan.employeeservice.dto.request.EmployeeRequest;
import com.ozeryavuzaslan.employeeservice.dto.response.EmployeeResponse;
import com.ozeryavuzaslan.employeeservice.dto.response.EmployeeWithDepartmentInfoResponse;
import com.ozeryavuzaslan.employeeservice.model.Employee;
import com.ozeryavuzaslan.employeeservice.repository.EmployeeRepository;
import com.ozeryavuzaslan.employeeservice.service.EmployeeFinderService;
import com.ozeryavuzaslan.employeeservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final EmployeeFinderService employeeFinderService;
    private final EmployeeConverter employeeConverter;
    private final DepartmentServiceClient departmentServiceClient;
    private final DepartmentServiceOpenFeignClient departmentServiceOpenFeignClient;
    private final OrganizationServiceOpenFeignClient organizationServiceOpenFeignClient;
    private final List<EmployeeWithDepartmentInfoResponse> employeeWithDepartmentInfoResponseList;
    private final HardCodedDepartmentInfoConverter hardCodedDepartmentInfoConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImp.class);

    @Override
    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest, Map<String, String> headers) {
        departmentServiceClient.getDepartmentInfo(employeeRequest.getDepartmentCode(), headers);
        return modelMapper
                .map(employeeRepository
                        .save(modelMapper
                                .map(employeeRequest, Employee.class)),
                        EmployeeResponse.class);
    }



    @Override
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public EmployeeWithDepartmentInfoResponse findOne(String employeeEmail, Map<String, String> headers) {
        LOGGER.info("inside findOne(String employeeEmail, Map<String, String> headers) method");
        Employee employee = employeeFinderService.findSpecificEmployee(employeeEmail);
        EmployeeWithDepartmentInfoResponse employeeWithDepartmentInfoResponse = modelMapper.map(employee, EmployeeWithDepartmentInfoResponse.class);
        modelMapper.map(departmentServiceClient.getDepartmentInfoByUsingWebClient(employee.getDepartmentCode(), headers), employeeWithDepartmentInfoResponse);
        modelMapper.map(organizationServiceOpenFeignClient.getOrganizationByCode(employee.getOrganizationCode()), employeeWithDepartmentInfoResponse);

        return employeeWithDepartmentInfoResponse;
    }

    @Override
    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public EmployeeWithDepartmentInfoResponse findOne(long employeeID, Map<String, String> headers) {
        Employee employee = employeeFinderService.findSpecificEmployee(employeeID);
        EmployeeWithDepartmentInfoResponse employeeWithDepartmentInfoResponse = modelMapper.map(employee, EmployeeWithDepartmentInfoResponse.class);
        modelMapper.map(departmentServiceClient.getDepartmentInfo(employee.getDepartmentCode(), headers).getBody(), employeeWithDepartmentInfoResponse);
        modelMapper.map(organizationServiceOpenFeignClient.getOrganizationByCode(employee.getOrganizationCode()), employeeWithDepartmentInfoResponse);

        return employeeWithDepartmentInfoResponse;
    }

    private EmployeeWithDepartmentInfoResponse getDefaultDepartment(long employeeID, Map<String, String> headers, Exception exception){
        Employee employee = employeeFinderService.findSpecificEmployee(employeeID);
        EmployeeWithDepartmentInfoResponse employeeWithDepartmentInfoResponse = modelMapper.map(employee, EmployeeWithDepartmentInfoResponse.class);
        modelMapper.map(hardCodedDepartmentInfoConverter.hardCodedDepartmentInfo(), employeeWithDepartmentInfoResponse);

        return employeeWithDepartmentInfoResponse;
    }

    private EmployeeWithDepartmentInfoResponse getDefaultDepartment(String employeeEmail, Map<String, String> headers, Exception exception){
        LOGGER.info("inside getDefaultDepartment(String employeeEmail, Map<String, String> headers, Exception exception) method");

        Employee employee = employeeFinderService.findSpecificEmployee(employeeEmail);
        EmployeeWithDepartmentInfoResponse employeeWithDepartmentInfoResponse = modelMapper.map(employee, EmployeeWithDepartmentInfoResponse.class);
        modelMapper.map(hardCodedDepartmentInfoConverter.hardCodedDepartmentInfo(), employeeWithDepartmentInfoResponse);

        return employeeWithDepartmentInfoResponse;
    }

    @Override
    public List<EmployeeResponse> findAll() {
        return employeeRepository
                .findAll()
                .stream()
                .map(department -> modelMapper.map(department, EmployeeResponse.class))
                .toList();
    }

    @Override
    public List<EmployeeWithDepartmentInfoResponse> findAllWithFullDepartmentInfo(Map<String, String> headers) {
        List<Employee> employeeList = employeeRepository.findAll();
        List<DepartmentDto> departmentDtoList = departmentServiceOpenFeignClient.getAllDepartments(headers);

        for (int i = 0; i < employeeList.size(); i++){
            EmployeeWithDepartmentInfoResponse employeeWithDepartmentInfoResponse = modelMapper.map(employeeList.get(i), EmployeeWithDepartmentInfoResponse.class);

            int finalI = i;
            modelMapper.map(departmentDtoList
                            .stream()
                            .filter(departmentDto -> departmentDto
                                    .getCode()
                                    .equals(employeeList
                                            .get(finalI)
                                            .getDepartmentCode()))
                            .findAny()
                            .orElse(null),
                    employeeWithDepartmentInfoResponse);
            employeeWithDepartmentInfoResponseList.add(employeeWithDepartmentInfoResponse);
        }

        return employeeWithDepartmentInfoResponseList;
    }

    @Override
    public EmployeeResponse updateEmployee(String employeeEmail, EmployeeRequest employeeRequest) {
        return modelMapper
                .map(employeeRepository
                                .save(employeeConverter
                                        .convert(employeeFinderService
                                                        .findSpecificEmployee(employeeEmail),
                                                employeeRequest)),
                        EmployeeResponse.class);
    }

    @Override
    public EmployeeResponse updateEmployee(long employeeID, EmployeeRequest employeeRequest) {
        return modelMapper
                .map(employeeRepository
                                .save(employeeConverter
                                        .convert(employeeFinderService
                                                        .findSpecificEmployee(employeeID),
                                                employeeRequest)),
                        EmployeeResponse.class);
    }

    @Override
    public void deleteEmployee(long employeeID) {
        employeeRepository.deleteById(employeeFinderService.findSpecificEmployee(employeeID).getId());
    }

    @Override
    public void deleteEmployee(String employeeEmail) {
        employeeRepository.deleteByEmail(employeeFinderService.findSpecificEmployee(employeeEmail).getEmail());
    }
}