package com.biletimcepte.controller.advice;

import com.biletimcepte.exception.*;
import lombok.Data;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static com.biletimcepte.util.Constants.USER_ALREADY_IN_THE_SYSTEM;

@Data
@RestControllerAdvice
public class GlobalExceptionHandler {
    private MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        setMessageSource(messageSource);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(UserNotFoundException exception) {
        return ResponseEntity.ok(new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BiletimCepteException.class)
    public ResponseEntity<ExceptionResponse> handle(BiletimCepteException exception) {
        String message = messageSource.getMessage(exception.getKey(), null, new Locale("en"));
        return ResponseEntity.ok(new ExceptionResponse(message, HttpStatus.BAD_REQUEST));
    }

    /*
    @ResponseBody
    @ExceptionHandler(UniqueConstraintException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String UniqueConstraintException(UniqueConstraintException exception) {
        return exception.getMessage();
    }
*/

    @ResponseBody
    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String UniqueConstraintException(PSQLException exception) {
        var errorCode = exception.getSQLState();

        if(errorCode.equals("23505")) {
            return USER_ALREADY_IN_THE_SYSTEM;
        }

        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(VoyageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String voyageNotFoundHandler(VoyageNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotEnoughAvailableSeatsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String NotEnoughAvailableSeatsException(NotEnoughAvailableSeatsException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String BookingNotFoundException(BookingNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EmptyTicketListException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String EmptyTicketListException(EmptyTicketListException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MaxAllowedMalePassengerNumException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String MaxAllowedMalePassengerNumException(MaxAllowedMalePassengerNumException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PaymentAlreadyDoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String PaymentAlreadyDoneException(PaymentAlreadyDoneException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String RoleNotFoundException(RoleNotFoundException exception) {
        return exception.getMessage();
    }
}