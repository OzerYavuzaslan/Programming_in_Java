package com.ozeryavuzaslan.departmentservice.controller.advice;

import com.ozeryavuzaslan.departmentservice.dto.response.ErrorDetails;
import com.ozeryavuzaslan.departmentservice.exception.DepartmentNotFoundException;
import com.ozeryavuzaslan.departmentservice.util.CustomMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.ozeryavuzaslan.departmentservice.util.Constants.*;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorDetails errorDetails;
    private final CustomMessageHandler customMessageHandler;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception exception, WebRequest request) {
        errorDetails
                .setErrorDetailsProperties(LocalDateTime.now(),
                        exception.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDetails> handleUniqueConstraintViolationException(Exception exception, WebRequest request){
        errorDetails
                .setErrorDetailsProperties(LocalDateTime.now(),
                        customMessageHandler
                                .returnProperMessage(ALREADY_IN_DEFINITION, exception.getMessage()),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(DepartmentNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleDepartmentNotFoundExceptions(Exception exception, WebRequest request) {
        errorDetails
                .setErrorDetailsProperties(LocalDateTime.now(),
                        customMessageHandler
                                .returnProperMessage(DEPARTMENT_NOT_FOUND_DEFINITION,
                                        exception.getMessage()),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String tmpExceptionMsg = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        errorDetails.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler.returnProperMessage(TOTAL_ERROR_DEFINITION, TOTAL_ERROR_DEFINITION) +
                        exception.getErrorCount() + " | " +
                        customMessageHandler.returnProperMessage(FIRST_ERROR_DEFINITION, FIRST_ERROR_DEFINITION)  +
                        customMessageHandler.returnProperMessage(tmpExceptionMsg, tmpExceptionMsg),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
