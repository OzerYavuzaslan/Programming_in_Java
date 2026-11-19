package com.omniflow.order.infra.web.exception;

import com.omniflow.order.application.exception.OrderNotFoundException;
import com.omniflow.order.domain.model.OrderError;
import com.omniflow.order.infra.persistence.mapper.OrderStatusCodec;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Global REST exception handler producing localized RFC 9457 {@link ProblemDetail} responses.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String PROPERTY_ERRORS = "errors";
    private static final String PROPERTY_ORDER_ID = "orderId";
    private static final String PROPERTY_TENANT_ID = "tenantId";
    private static final URI TYPE_VALIDATION_ERROR = URI.create("/problems/validation-error");
    private static final URI TYPE_INVALID_REQUEST = URI.create("/problems/invalid-request");
    private static final URI TYPE_MISSING_HEADER = URI.create("/problems/missing-header");
    private static final URI TYPE_ORDER_NOT_FOUND = URI.create("/problems/order-not-found");
    private static final URI TYPE_INVALID_STATE_TRANSITION = URI.create("/problems/invalid-state-transition");
    private static final URI TYPE_CONCURRENT_MODIFICATION = URI.create("/problems/concurrent-modification");
    private static final URI TYPE_INVALID_CANCELLATION_REASON = URI.create("/problems/invalid-cancellation-reason");
    private static final String KEY_VALIDATION_TITLE = "problem.validation.title";
    private static final String KEY_VALIDATION_DETAIL = "problem.validation.detail";
    private static final String KEY_INVALID_REQUEST_TITLE = "problem.invalid_request.title";
    private static final String KEY_INVALID_REQUEST_DETAIL = "problem.invalid_request.detail";
    private static final String KEY_MISSING_HEADER_TITLE = "problem.missing_header.title";
    private static final String KEY_MISSING_HEADER_DETAIL = "problem.missing_header.detail";
    private static final String KEY_ORDER_NOT_FOUND_TITLE = "problem.order_not_found.title";
    private static final String KEY_ORDER_NOT_FOUND_DETAIL = "problem.order_not_found.detail";
    private static final String KEY_INVALID_STATE_TITLE = "problem.invalid_state_transition.title";
    private static final String KEY_INVALID_STATE_DETAIL = "problem.invalid_state_transition.detail";
    private static final String KEY_CONCURRENT_MOD_TITLE = "problem.concurrent_modification.title";
    private static final String KEY_CONCURRENT_MOD_DETAIL = "problem.concurrent_modification.detail";
    private static final String KEY_INVALID_CANCEL_TITLE = "problem.invalid_cancellation_reason.title";
    private static final String KEY_INVALID_CANCEL_DETAIL = "problem.invalid_cancellation_reason.detail";
    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException methodArgumentNotValidException,
                                                                  @NonNull HttpHeaders httpHeaders,
                                                                  @NonNull HttpStatusCode httpStatusCode,
                                                                  @NonNull WebRequest webRequest) {
        Map<String, String> fieldErrorMap = new LinkedHashMap<>();
        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            fieldErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail problemDetail = createLocalizedProblemDetail(
                HttpStatus.BAD_REQUEST,
                TYPE_VALIDATION_ERROR,
                KEY_VALIDATION_TITLE,
                KEY_VALIDATION_DETAIL
        );
        problemDetail.setProperty(PROPERTY_ERRORS, fieldErrorMap);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(@NonNull ServletRequestBindingException servletRequestBindingException,
                                                                          @NonNull HttpHeaders httpHeaders,
                                                                          @NonNull HttpStatusCode httpStatusCode,
                                                                          @NonNull WebRequest webRequest) {
        if (servletRequestBindingException instanceof MissingRequestHeaderException missingRequestHeaderException) {
            ProblemDetail problemDetail = createLocalizedProblemDetail(
                    HttpStatus.BAD_REQUEST,
                    TYPE_MISSING_HEADER,
                    KEY_MISSING_HEADER_TITLE,
                    KEY_MISSING_HEADER_DETAIL,
                    missingRequestHeaderException.getHeaderName());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
        }

        return super.handleServletRequestBindingException(servletRequestBindingException, httpHeaders, httpStatusCode, webRequest);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException constraintViolationException) {
        Map<String, String> violationMap = new LinkedHashMap<>();
        constraintViolationException.getConstraintViolations()
                .forEach(constraintViolation ->
                        violationMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));

        ProblemDetail problemDetail = createLocalizedProblemDetail(
                HttpStatus.BAD_REQUEST,
                TYPE_VALIDATION_ERROR,
                KEY_VALIDATION_TITLE,
                KEY_VALIDATION_DETAIL);

        problemDetail.setProperty(PROPERTY_ERRORS, violationMap);
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException illegalArgumentException) {
        return createLocalizedProblemDetail(
                HttpStatus.BAD_REQUEST,
                TYPE_INVALID_REQUEST,
                KEY_INVALID_REQUEST_TITLE,
                KEY_INVALID_REQUEST_DETAIL,
                illegalArgumentException.getMessage()
        );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFound(OrderNotFoundException orderNotFoundException) {
        ProblemDetail problemDetail = createLocalizedProblemDetail(HttpStatus.NOT_FOUND,
                TYPE_ORDER_NOT_FOUND,
                KEY_ORDER_NOT_FOUND_TITLE,
                KEY_ORDER_NOT_FOUND_DETAIL,
                orderNotFoundException.getOrderId(),
                orderNotFoundException.getTenantId());

        problemDetail.setProperty(PROPERTY_ORDER_ID, orderNotFoundException.getOrderId());
        problemDetail.setProperty(PROPERTY_TENANT_ID, orderNotFoundException.getTenantId());
        return problemDetail;
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException objectOptimisticLockingFailureException) {
        return createLocalizedProblemDetail(
                HttpStatus.CONFLICT,
                TYPE_CONCURRENT_MODIFICATION,
                KEY_CONCURRENT_MOD_TITLE,
                KEY_CONCURRENT_MOD_DETAIL
        );
    }

    public ProblemDetail toProblemDetail(OrderError orderError) {
        return switch (orderError) {
            case OrderError.InvalidStateTransition invalidStateTransition -> createLocalizedProblemDetail(
                    HttpStatus.CONFLICT,
                    TYPE_INVALID_STATE_TRANSITION,
                    KEY_INVALID_STATE_TITLE,
                    KEY_INVALID_STATE_DETAIL,
                    OrderStatusCodec.toCode(invalidStateTransition.currentStatus()),
                    OrderStatusCodec.toCode(invalidStateTransition.targetStatus()));

            case OrderError.InvalidCancellationReason invalidCancellationReason -> createLocalizedProblemDetail(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    TYPE_INVALID_CANCELLATION_REASON,
                    KEY_INVALID_CANCEL_TITLE,
                    KEY_INVALID_CANCEL_DETAIL,
                    invalidCancellationReason.message()
            );
        };
    }

    private ProblemDetail createLocalizedProblemDetail(HttpStatus httpStatus,
                                                       URI typeUri,
                                                       String titleKey,
                                                       String detailKey,
                                                       Object... detailArgs) {
        Locale locale = LocaleContextHolder.getLocale();
        String localizedTitle = messageSource.getMessage(titleKey, null, titleKey, locale);
        String localizedDetail = messageSource.getMessage(
                detailKey,
                Objects.nonNull(detailArgs) ? detailArgs : new Object[0],
                detailKey,
                locale);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, localizedDetail);
        problemDetail.setType(typeUri);
        problemDetail.setTitle(localizedTitle);
        return problemDetail;
    }
}