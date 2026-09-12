package com.securepay.customer.exception;

import com.securepay.customer.domain.CustomerId;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(CustomerId customerId) {
        super("Customer not found: " + customerId.value());
    }
}