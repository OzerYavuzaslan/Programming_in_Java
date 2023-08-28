package com.ozeryavuzaslan.orderservice.controller.advice;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.orderservice.util.CustomMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    @Value("${total.error.message}")
    private String totalError;

    @Value("${first.error.message}")
    private String firstError;

    private final ErrorDetailsDTO errorDetails;
    private final CustomMessageHandler customMessageHandler;
    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String tmpExceptionMsg = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        errorDetails.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler.returnProperMessage(totalError, totalError) +
                        exception.getErrorCount() + " | " +  //TODO: getAllErrors() ile bütün error mesajları getir.
                        customMessageHandler.returnProperMessage(firstError, firstError)  +
                        customMessageHandler.returnProperMessage(tmpExceptionMsg, tmpExceptionMsg),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
