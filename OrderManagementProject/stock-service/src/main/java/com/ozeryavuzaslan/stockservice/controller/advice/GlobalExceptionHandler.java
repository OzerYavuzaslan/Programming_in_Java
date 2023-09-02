package com.ozeryavuzaslan.stockservice.controller.advice;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.util.CustomMessageHandler;
import com.ozeryavuzaslan.basedomains.util.CustomStringBuilder;
import com.ozeryavuzaslan.stockservice.exception.CategoryNotFoundException;
import com.ozeryavuzaslan.stockservice.exception.ProductAmountNotEnoughException;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Value;
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

import static com.ozeryavuzaslan.basedomains.util.Constants.*;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorDetailsDTO errorDetailsDTO;
    private final CustomStringBuilder customStringBuilder;
    private final CustomMessageHandler customMessageHandler;

    @Value("${unique.constraint.violation}")
    private String uniqueConstraintViolationExceptionMsg;

    @Value("${unique.constraint.violation.sql.code}")
    private String uniqueConstraintViolationSQLStatusCode;

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleUniqueConstraintViolationException(Exception exception, WebRequest request, PSQLException psqlException){
        String tmpExceptionMsg;

        if (psqlException != null)
            if (psqlException.getSQLState().equals(uniqueConstraintViolationSQLStatusCode))
                tmpExceptionMsg = uniqueConstraintViolationExceptionMsg;
            else
                tmpExceptionMsg = exception.getMessage();
        else
            tmpExceptionMsg = exception.getMessage();

        errorDetailsDTO
                .setErrorDetailsProperties(LocalDateTime.now(),
                        customMessageHandler
                                .returnProperMessage(ALREADY_IN_DEFINITION, tmpExceptionMsg),
                        request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(ProductAmountNotEnoughException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleQuantityNotEnoughException(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(QUANTITY_AMOUNT_NOT_ENOUGH,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler({CategoryNotFoundException.class, StockNotFoundException.class})
    public final ResponseEntity<ErrorDetailsDTO> handleNotFoundExceptions(Exception exception, WebRequest request) {
        boolean isStockException = exception.getClass().getName().equals(StockNotFoundException.class.getName());
        String tmpExceptionMessage;

        if (isStockException)
            tmpExceptionMessage = STOCK_NOT_FOUND;
        else
            tmpExceptionMessage = CATEGORY_NOT_FOUND;

        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(tmpExceptionMessage,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

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
