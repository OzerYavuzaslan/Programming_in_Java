package com.ozeryavuzaslan.paymentservice.controller.advice;

import com.ozeryavuzaslan.basedomains.dto.ErrorDetailsDTO;
import com.ozeryavuzaslan.basedomains.util.CustomMessageHandler;
import com.ozeryavuzaslan.basedomains.util.CustomStringBuilder;
import com.ozeryavuzaslan.paymentservice.exception.PaymentNotFoundException;
import com.ozeryavuzaslan.paymentservice.exception.RefundAmountExceedsException;
import com.ozeryavuzaslan.paymentservice.exception.RefundNotFoundException;
import com.stripe.exception.StripeException;
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

    @Value("${total.errors}")
    private String totalErrors;

    @Value("${first.error}")
    private String firstError;

    @Value("${unique.constraint.violation}")
    private String uniqueConstraintViolationExceptionMsg;

    @Value("${unique.constraint.violation.sql.code}")
    private String uniqueConstraintViolationSQLStatusCode;

    @Value("${payment.not.found.exception.message}")
    private String paymentNotFoundExceptionMsg;

    @Value("${refund.not.found.exception.message}")
    private String refundNotFoundExceptionMsg;

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
    public final ResponseEntity<ErrorDetailsDTO> handleUniqueConstraintViolationException(Exception exception, WebRequest request, PSQLException psqlException){
        String tmpExceptionMsg = exception.getMessage();

        if (psqlException != null && psqlException.getSQLState().equals(uniqueConstraintViolationSQLStatusCode))
            tmpExceptionMsg = uniqueConstraintViolationExceptionMsg;

        errorDetailsDTO
                .setErrorDetailsProperties(LocalDateTime.now(),
                        customMessageHandler
                                .returnProperMessage(uniqueConstraintViolationExceptionMsg, tmpExceptionMsg),
                        request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler({PaymentNotFoundException.class, RefundNotFoundException.class})
    public final ResponseEntity<ErrorDetailsDTO> handleNotFoundException(Exception exception, WebRequest request) {
        boolean isPaymentException = exception.getClass().getName().equals(PaymentNotFoundException.class.getName());
        String tmpExceptionMessage;

        if (isPaymentException)
            tmpExceptionMessage = paymentNotFoundExceptionMsg;
        else
            tmpExceptionMessage = refundNotFoundExceptionMsg;

        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler
                        .returnProperMessage(tmpExceptionMessage,
                                exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(StripeException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleQuantityNotEnoughException(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler.returnProperMessage(exception.getMessage(), exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler(RefundAmountExceedsException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleRefundAmountExceedsException(Exception exception, WebRequest request) {
        errorDetailsDTO.setErrorDetailsProperties(LocalDateTime.now(),
                customMessageHandler.returnProperMessage(exception.getMessage(), exception.getMessage()),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_MODIFIED);
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