package com.securepay.customer.exception;

public class CustomerAlreadyExistsException
        extends CustomerDomainException {

    public CustomerAlreadyExistsException() {
        super("Customer with email already exists");
    }
}