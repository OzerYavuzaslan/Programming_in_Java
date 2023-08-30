package com.ozeryavuzaslan.orderservice.controller.advice;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.util.CustomMessageHandler;
import com.ozeryavuzaslan.basedomains.util.CustomStringBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.ozeryavuzaslan.basedomains.util.Constants.FIRST_ERROR;
import static com.ozeryavuzaslan.basedomains.util.Constants.TOTAL_ERRORS;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    private final ErrorDetailsDTO errorDetailsDTO;
    private final CustomStringBuilder customStringBuilder;
    private final CustomMessageHandler customMessageHandler;

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String tmpExceptionMsg = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler.returnProperMessage(FIRST_ERROR, FIRST_ERROR)  +
                        customMessageHandler.returnProperMessage(tmpExceptionMsg, tmpExceptionMsg) + " | " +
                        customMessageHandler.returnProperMessage(TOTAL_ERRORS, TOTAL_ERRORS) +
                        exception.getErrorCount() + " --> " + customStringBuilder.getDefaultExceptionMessages(exception),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.BAD_REQUEST);
    }
}