package com.ozeryavuzaslan.stockservice.controller.advice;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.util.CustomMessageHandler;
import com.ozeryavuzaslan.stockservice.exception.CategoryNotFoundException;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static com.ozeryavuzaslan.basedomains.util.Constants.CATEGORY_NOT_FOUND;
import static com.ozeryavuzaslan.basedomains.util.Constants.STOCK_NOT_FOUND;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorDetailsDTO errorDetails;
    private final CustomMessageHandler customMessageHandler;

    @ResponseBody
    @ExceptionHandler({CategoryNotFoundException.class, StockNotFoundException.class})
    public final ResponseEntity<ErrorDetailsDTO> handleNotFoundExceptions(Exception exception, WebRequest request) {
        boolean isStockException = exception.getClass().getName().equals(StockNotFoundException.class.getName());
        String tmpExceptionMessage;

        if (isStockException)
            tmpExceptionMessage = STOCK_NOT_FOUND;
        else
            tmpExceptionMessage = CATEGORY_NOT_FOUND;

        errorDetails.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(tmpExceptionMessage,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
