package com.ozeryavuzaslan.employeeservice.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.employeeservice.dto.response.ErrorDetails;
import com.ozeryavuzaslan.employeeservice.util.JSONStringFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExceptionResponseJsonToObject {
    private final JSONStringFinder jsonStringFinder;
    private final ObjectMapper objectMapper;
    public ErrorDetails mapExceptionResponseToErrorDetails(String jsonStr) throws JsonProcessingException {
        return objectMapper.readValue(jsonStringFinder
                .returnProperJSONStr(jsonStr),
                ErrorDetails.class);
    }
}