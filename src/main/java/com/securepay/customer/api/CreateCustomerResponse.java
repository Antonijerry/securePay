package com.securepay.customer.api;

import com.securepay.customer.domain.CustomerId;

import java.util.UUID;

public record CreateCustomerResponse(
        UUID id
) {

    public static CreateCustomerResponse from(CustomerId customerId) {
        return new CreateCustomerResponse(customerId.value());
    }
}