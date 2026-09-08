package com.securepay.customer.exception;

public class CustomerDomainException extends RuntimeException {

    public CustomerDomainException(String message) {
        super(message);
    }
}