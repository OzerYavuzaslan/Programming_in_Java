package com.ozeryavuzaslan.employeeservice.client;

import com.ozeryavuzaslan.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.ozeryavuzaslan.employeeservice.util.Constants.ORGANIZATION_REQUEST_MAPPING_BY_CODE;


//@FeignClient(url = BASE_DEPARTMENT_SERVICE_URL, value = "DEPARTMENT-SERVICE")
@FeignClient(name = "ORGANIZATION-SERVICE")
public interface OrganizationServiceOpenFeignClient {
  //  @GetMapping(DEPARTMENT_REQUEST_MAPPING + DEPARTMENT_GET_BY_CODE_ENDPOINT + "/{code}")

    @RequestMapping(method = RequestMethod.GET, value = ORGANIZATION_REQUEST_MAPPING_BY_CODE + "{code}")
    OrganizationDto getOrganizationByCode(@PathVariable("code") String code);
}