package com.securepay.customer.application;

public record CreateCustomerCommand(
        String email,
        String firstName,
        String lastName
) {
}