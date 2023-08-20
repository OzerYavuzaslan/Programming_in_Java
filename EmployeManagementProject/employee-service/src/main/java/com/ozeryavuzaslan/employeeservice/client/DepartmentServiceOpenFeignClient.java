package com.ozeryavuzaslan.employeeservice.client;

import com.ozeryavuzaslan.employeeservice.dto.DepartmentDto;
import com.ozeryavuzaslan.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.ozeryavuzaslan.employeeservice.util.Constants.*;


//@FeignClient(url = BASE_DEPARTMENT_SERVICE_URL, value = "DEPARTMENT-SERVICE")
@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentServiceOpenFeignClient {
    @GetMapping(DEPARTMENT_REQUEST_MAPPING + DEPARTMENT_GET_BY_CODE_ENDPOINT + "/{code}")
    DepartmentDto getDepartment(@PathVariable("code") String code);

    /*
    @Headers({
            "Accept: application/json",
            "Accept-Language: tr"
    })
    */
  //  @GetMapping(DEPARTMENT_REQUEST_MAPPING)
    @RequestMapping(method = RequestMethod.GET, value = DEPARTMENT_REQUEST_MAPPING)
    List<DepartmentDto> getAllDepartments(@RequestHeader Map<String, String> headerMap);

    @RequestMapping(method = RequestMethod.GET, value = ORGANIZATION_REQUEST_MAPPING_BY_CODE)
    OrganizationDto getOrganizationByCode(@PathVariable("code") String code);
}