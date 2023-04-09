package com.ozeryavuzaslan.webservices.restfulwebservicesProject.controller.advice;


import com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response.ErrorDetails;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.exception.PostNotFoundException;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.exception.UserNotFoundException;
import com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.CustomMessageHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
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

import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.*;

@Getter
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final CustomMessageHandler customMessageHandler;
    private final ErrorDetails errorDetails;
/*
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), request.getTitle(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundExceptions(Exception exception, WebRequest request) {
        errorDetails.setErrorDetailsProperties(LocalDateTime.now(), getCustomMessageHandler().returnProperMessage(NOT_FOUND_DEFINITION, exception.getMessage()), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(PostNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handlePostNotFoundExceptions(Exception exception, WebRequest request) {
        errorDetails.setErrorDetailsProperties(LocalDateTime.now(), getCustomMessageHandler().returnProperMessage(POST_NOT_FOUND_DEFINITION, exception.getMessage()), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(PSQLException.class)
    public final ResponseEntity<ErrorDetails> userAlreadyExistsHandler(PSQLException exception, WebRequest request) {
        var errorCode= exception.getSQLState();

        if(errorCode.equals(PSQL_EXCEPTION_CODE)) {
            errorDetails.setErrorDetailsProperties(LocalDateTime.now(), getCustomMessageHandler().returnProperMessage(ALREADY_IN_DEFINITION, ALREADY_IN_DEFINITION), request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
        }
        else {
            errorDetails.setErrorDetailsProperties(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
/*
        Map<String, String> errorsMap = new HashMap<>();
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        errorList
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errorsMap.put(fieldName, message);
                        }
                        );
*/

        String tmpExceptionMsg = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        errorDetails.setErrorDetailsProperties(LocalDateTime.now(),
                getCustomMessageHandler().returnProperMessage(TOTAL_ERROR_DEFINITION, TOTAL_ERROR_DEFINITION) +
                        exception.getErrorCount() + " | " +
                        getCustomMessageHandler().returnProperMessage(FIRST_ERROR_DEFINITION, FIRST_ERROR_DEFINITION)  +
                        getCustomMessageHandler().returnProperMessage(tmpExceptionMsg, tmpExceptionMsg),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
