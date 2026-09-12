package com.securepay.customer.api;

import com.securepay.common.api.ApiErrorResponse;
import com.securepay.customer.exception.CustomerAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleCustomerAlreadyExists(
            CustomerAlreadyExistsException ex) {

        return new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Customer already exists",
                ex.getMessage()
        );
    }
}