package com.securepay.customer.api;

import com.securepay.customer.domain.Customer;
import com.securepay.customer.domain.CustomerStatus;

import java.time.Instant;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        CustomerStatus status,
        Instant createdAt,
        Instant updatedAt
) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId().value(),
                customer.getEmail().value(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    
}