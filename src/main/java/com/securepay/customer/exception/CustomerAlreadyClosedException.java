package com.securepay.customer.exception;

public class CustomerAlreadyClosedException
        extends CustomerDomainException {

    public CustomerAlreadyClosedException() {
        super("Customer is already closed");
    }
}