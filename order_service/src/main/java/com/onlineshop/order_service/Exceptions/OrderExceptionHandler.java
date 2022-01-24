package com.onlineshop.order_service.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception handler that handles all the exceptions.
 */
@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(value = {OrderNotFoundException.class, CustomerEmailIsMissingException.class, CustomerDoesNotExistException.class,
    CustomerIdIsZeroOrNegative.class, MissingOriginalProductIdException.class, NegativeOrderTotalAmountException.class, NegativeProductOriginalIdException.class,
    NegativeProductPriceException.class, NegativeProductQuantityException.class, OrderIsNotPendingOrCancelledException.class,
    ProductListInOrderIsEmptyException.class, ProductQuantityInsufficientException.class, ProductQuantityIsZeroException.class, StatusEnumDoesNotExist.class})
    public final ResponseStatusException handleException(Exception exception) {
        if (exception instanceof OrderNotFoundException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof CustomerEmailIsMissingException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof CustomerDoesNotExistException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof CustomerIdIsZeroOrNegative) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof MissingOriginalProductIdException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof NegativeOrderTotalAmountException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof NegativeProductOriginalIdException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof NegativeProductPriceException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof NegativeProductQuantityException) {
        return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof OrderIsNotPendingOrCancelledException) {
        return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof ProductListInOrderIsEmptyException) {
        return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof ProductQuantityInsufficientException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof ProductQuantityIsZeroException) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof StatusEnumDoesNotExist) {
            return handleExceptionInternally(HttpStatus.NOT_FOUND, exception);
        } else {
            return handleExceptionInternally(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    private ResponseStatusException handleExceptionInternally(HttpStatus httpStatus, Exception exception) {
        return new ResponseStatusException(httpStatus, exception.getMessage());
    }

}
