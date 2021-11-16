package jvm.pablohdz.todoapi.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jvm.pablohdz.todoapi.exceptions.DataAlreadyRegistered;
import jvm.pablohdz.todoapi.exceptions.DataNotFoundException;
import jvm.pablohdz.todoapi.exceptions.DuplicateUserData;

@ControllerAdvice
public class UserAdminServiceExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(value = {DuplicateUserData.class, IllegalArgumentException.class})
    protected ResponseEntity<?> handleConflict(
            RuntimeException exception, WebRequest request
    )
    {
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request
        );
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    protected ResponseEntity<?> handleNotFoundException(
            DataNotFoundException exception, WebRequest request
    )
    {
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request
        );
    }

    @ExceptionHandler(value = {DataAlreadyRegistered.class})
    protected ResponseEntity<?> handleDataAlreadyRegistered(
            DataAlreadyRegistered exception, WebRequest request
    )
    {
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request
        );
    }
}
