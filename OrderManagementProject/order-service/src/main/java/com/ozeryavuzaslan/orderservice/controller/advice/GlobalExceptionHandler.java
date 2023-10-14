package com.ozeryavuzaslan.orderservice.controller.advice;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.util.CustomMessageHandler;
import com.ozeryavuzaslan.basedomains.util.CustomStringBuilder;
import com.ozeryavuzaslan.basedomains.util.HandledHTTPExceptions;
import com.ozeryavuzaslan.orderservice.exception.PaymentServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.ReserveStockServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.RevenueServiceNotRunningException;
import com.ozeryavuzaslan.orderservice.exception.StockServiceNotRunningException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${total.errors}")
    private String totalErrors;

    @Value("${first.error}")
    private String firstError;

    @Value("${reserve.stock.service.not.running.exception}" + "${base.service.not.running.exception.msg}")
    private String reserveStockServiceNotRunning;

    @Value("${stock.service.not.running.exception}" + "${base.service.not.running.exception.msg}")
    private String stockServiceNotRunning;

    @Value("${payment.service.not.running.exception}" + "${base.service.not.running.exception.msg}")
    private String paymentServiceNotRunning;

    @Value("${revenue.service.not.running.exception}" + "${base.service.not.running.exception.msg}")
    private String revenueServiceNotRunning;

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
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleExceptionsOfMicroservices(Exception exception, WebRequest request) {
        String[] microserviceResponse = exception.getMessage().split("_");
        String tmpExceptionMsg = exception.getMessage();
        int httpStatusCode = HttpStatus.NOT_ACCEPTABLE.value();

        if (microserviceResponse.length == 2) {
            tmpExceptionMsg = microserviceResponse[0];
            httpStatusCode = Integer.parseInt(microserviceResponse[1]);
        }

        errorDetailsDTO
                .setErrorDetailsProperties(LocalDateTime.now(),
                        tmpExceptionMsg,
                        request.getDescription(false));
        return new ResponseEntity<>(errorDetailsDTO, HandledHTTPExceptions.getProperHTTPStatus(httpStatusCode));
    }

    @ResponseBody
    @ExceptionHandler(ReserveStockServiceNotRunningException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleReserveStockServiceNotRunningException(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(reserveStockServiceNotRunning,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler(StockServiceNotRunningException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleStockServiceNotRunningException(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(reserveStockServiceNotRunning,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler(PaymentServiceNotRunningException.class)
    public final ResponseEntity<ErrorDetailsDTO> handlePaymentServiceNotRunningException(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(paymentServiceNotRunning,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler(RevenueServiceNotRunningException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleRevenueServiceNotRunningException(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(revenueServiceNotRunning,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_ACCEPTABLE);
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