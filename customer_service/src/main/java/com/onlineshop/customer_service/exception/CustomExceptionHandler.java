package com.onlineshop.customer_service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom Exception Handler that handles errors in REST Requests.
 * @author Nico Welsch
 * @version 1.0
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request)
    {
        return handleExceptionInternal(ex, "Customer Not Found ",new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
