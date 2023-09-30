package com.ozeryavuzaslan.revenueservice.controller.advice;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.util.CustomMessageHandler;
import com.ozeryavuzaslan.basedomains.util.CustomStringBuilder;
import com.ozeryavuzaslan.revenueservice.exception.TaxRateNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
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

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorDetailsDTO errorDetailsDTO;
    private final CustomStringBuilder customStringBuilder;
    private final CustomMessageHandler customMessageHandler;

    @Value("${total.errors}")
    private String totalErrors;

    @Value("${first.error}")
    private String firstError;

    @Value("${tax.rate.not.found}")
    private String taxRateNotFound;

    @Value("${unique.constraint.violation.tax.rate.service.impl}")
    private String uniqueConstraintViolationExceptionFromTaxRateServiceImplMsg;

    @Value("${unique.constraint.violation.sql.code}")
    private String uniqueConstraintViolationSQLStatusCode;

    @Value("${data.integrity.violation.exception.tax.rate.service.impl.class}")
    private String uniqueConstraintViolationFromTaxRateServiceImplClass;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetailsDTO> handleAllException(Exception exception, WebRequest request) {
        System.err.println("Exception: " + exception);
        System.err.println("WebRequest: " + request);

        errorDetailsDTO
                .setErrorDetailsProperties(LocalDateTime.now(),
                        exception.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler({DataAccessException.class, EntityNotFoundException.class, NonTransientDataAccessException.class})
    public final ResponseEntity<ErrorDetailsDTO> handleSQLExceptions(Exception exception, WebRequest request) {
        errorDetailsDTO
                .setErrorDetailsProperties(LocalDateTime.now(),
                        exception.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleUniqueConstraintViolationException(DataIntegrityViolationException exception, WebRequest request) {
        Throwable rootCause = exception.getRootCause();
        String tmpExceptionMsg = exception.getMessage();
        StackTraceElement[] stackTraceElements = exception.getStackTrace();

        if (rootCause instanceof SQLIntegrityConstraintViolationException &&
                uniqueConstraintViolationSQLStatusCode.equals(((SQLIntegrityConstraintViolationException) rootCause).getSQLState())) {
            for (StackTraceElement element : stackTraceElements) {
                if (uniqueConstraintViolationFromTaxRateServiceImplClass.equals(element.getClassName())) {
                    tmpExceptionMsg = uniqueConstraintViolationExceptionFromTaxRateServiceImplMsg;
                    break;
                }
            }
        }

        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler.returnProperMessage(tmpExceptionMsg, tmpExceptionMsg),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler({TaxRateNotFoundException.class})
    public final ResponseEntity<ErrorDetailsDTO> handleNotFoundExceptions(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler.returnProperMessage(taxRateNotFound, exception.getMessage()), request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatusCode status,
                                                                  WebRequest request) {
        String tmpExceptionMsg = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler.returnProperMessage(firstError, firstError)  +
                        customMessageHandler.returnProperMessage(tmpExceptionMsg, tmpExceptionMsg) + " | " +
                        customMessageHandler.returnProperMessage(totalErrors, totalErrors) +
                        exception.getErrorCount() + " --> " + customStringBuilder.getDefaultExceptionMessages(exception),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.BAD_REQUEST);
    }
}
