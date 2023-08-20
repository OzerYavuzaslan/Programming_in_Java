package com.ozeryavuzaslan.employeeservice.client.implementation;

import com.ozeryavuzaslan.employeeservice.client.DepartmentServiceClient;
import com.ozeryavuzaslan.employeeservice.dto.DepartmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static com.ozeryavuzaslan.employeeservice.util.Constants.*;

@Service
@RequiredArgsConstructor
public class DepartmentServiceClientImp implements DepartmentServiceClient {
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public ResponseEntity<DepartmentDto> getDepartmentInfo(String employeeDepartmentCode, Map<String, String> headers){
        httpHeaders.set("Accept", headers.get("accept"));
        httpHeaders.set("Accept-Language", headers.get("accept-language"));
        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(BASE_DEPARTMENT_SERVICE_URL +
                DEPARTMENT_REQUEST_MAPPING +
                DEPARTMENT_GET_BY_CODE_ENDPOINT + "/" +
                employeeDepartmentCode,
                HttpMethod.GET,
                requestEntity,
                DepartmentDto.class);

        /*
        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity(BASE_DEPARTMENT_SERVICE_URL +
                DEPARTMENT_REQUEST_MAPPING +
                DEPARTMENT_GET_BY_CODE_ENDPOINT + "/" +
                employeeDepartmentCode, DepartmentDto.class);
        return responseEntity.getBody();
        */
    }

    public DepartmentDto getDepartmentInfoByUsingWebClient(String employeeDepartmentCode, Map<String, String> headers){
        WebClient webClient = WebClient.builder().defaultHeaders(httpHeaders -> {
            httpHeaders.set("Accept", headers.get("accept"));
            httpHeaders.set("Accept-Language", headers.get("accept-language"));
        }).build();

        return webClient
                .get()
                .uri(BASE_DEPARTMENT_SERVICE_URL +
                        DEPARTMENT_REQUEST_MAPPING +
                        DEPARTMENT_GET_BY_CODE_ENDPOINT + "/" +
                        employeeDepartmentCode)
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();
    }
}