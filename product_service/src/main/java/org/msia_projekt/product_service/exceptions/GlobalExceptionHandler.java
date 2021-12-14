package org.msia_projekt.product_service.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = { ArticleDoesntExistsException.class })
    public final ResponseStatusException handleException(Exception ex) {
//        if (ex instanceof ArticleDoesntExistsException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, ex);
//        } else {
//            HttpStatus status = HttpStatus.NOT_FOUND;
//            ArticleDoesntExistsException articleDoesntExistsException = (ArticleDoesntExistsException) ex;
//
//            return new ResponseStatusException(status, articleDoesntExistsException.getMessage());
//        }
    }

    private ResponseStatusException handleExceptionInternally(HttpStatus httpStatus, Exception exception) {
        log.error(exception.getLocalizedMessage());

        return new ResponseStatusException(httpStatus, exception.getMessage());

    }

}