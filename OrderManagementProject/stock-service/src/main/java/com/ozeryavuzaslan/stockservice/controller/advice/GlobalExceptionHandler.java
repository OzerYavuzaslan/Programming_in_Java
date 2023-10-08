package com.ozeryavuzaslan.stockservice.controller.advice;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.util.CustomMessageHandler;
import com.ozeryavuzaslan.basedomains.util.CustomStringBuilder;
import com.ozeryavuzaslan.stockservice.exception.CategoryNotFoundException;
import com.ozeryavuzaslan.stockservice.exception.ProductAmountNotEnoughException;
import com.ozeryavuzaslan.stockservice.exception.ReservedStockNotFound;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    @Value("${total.errors}")
    private String totalErrors;

    @Value("${first.error}")
    private String firstError;

    @Value("${stock.amount.not.enough}")
    private String stockAmountNotEnough;

    @Value("${stock.not.found}")
    private String stockNotFound;

    @Value("${category.not.found}")
    private String categoryNotFound;

    @Value("${stock.reserved.stock.list.not.found}")
    private String reservedStocksNotFound;

    @Value("${reserve.stock.unique.constraint.violation}")
    private String uniqueConstraintViolationExceptionMsgForReserveStock;

    @Value("${data.integrity.violation.exception.tax.stock.service.impl.method.name}")
    private String reserveStockMethodName;

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
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleUniqueConstraintViolationException(DataIntegrityViolationException exception, WebRequest request, PSQLException psqlException) {
        String tmpExceptionMsg = exception.getMessage();
        StackTraceElement[] stackTraceElements = exception.getStackTrace();

        if (psqlException != null && psqlException.getSQLState().equals(uniqueConstraintViolationSQLStatusCode)) {
            tmpExceptionMsg = uniqueConstraintViolationExceptionMsg;

            for (StackTraceElement element : stackTraceElements) {
                if (reserveStockMethodName.equals(element.getMethodName())) {
                    tmpExceptionMsg = uniqueConstraintViolationExceptionMsgForReserveStock;
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
    @ExceptionHandler(ProductAmountNotEnoughException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleQuantityNotEnoughException(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(stockAmountNotEnough,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler({CategoryNotFoundException.class, StockNotFoundException.class, ReservedStockNotFound.class})
    public final ResponseEntity<ErrorDetailsDTO> handleNotFoundExceptions(Exception exception, WebRequest request) {
        String className = exception.getClass().getName();
        String tmpExceptionMessage;

        if (className.equalsIgnoreCase(StockNotFoundException.class.getName()))
            tmpExceptionMessage = stockNotFound;
        else if (className.equalsIgnoreCase(CategoryNotFoundException.class.getName()))
            tmpExceptionMessage = categoryNotFound;
        else
            tmpExceptionMessage = reservedStocksNotFound;

        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(tmpExceptionMessage, exception.getMessage()), request.getDescription(false));

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
                customMessageHandler.returnProperMessage(firstError, firstError) +
                        customMessageHandler.returnProperMessage(tmpExceptionMsg, tmpExceptionMsg) + " | " +
                        customMessageHandler.returnProperMessage(totalErrors, totalErrors) +
                        exception.getErrorCount() + " --> " + customStringBuilder.getDefaultExceptionMessages(exception),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.BAD_REQUEST);
    }
}