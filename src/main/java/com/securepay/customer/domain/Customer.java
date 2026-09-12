package com.securepay.customer.domain;

import com.securepay.customer.exception.CustomerAlreadyClosedException;

import java.time.Instant;
import java.util.Objects;

public final class Customer {

    private final CustomerId id;
    private final Email email;
    private final String firstName;
    private final String lastName;
    private final Instant createdAt;

    private CustomerStatus status;
    private Instant updatedAt;

    private Customer(
            CustomerId id,
            Email email,
            String firstName,
            String lastName,
            Instant createdAt
    ) {
        this.id = Objects.requireNonNull(id, "Customer ID cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.firstName = requireText(firstName, "First name");
        this.lastName = requireText(lastName, "Last name");
        this.createdAt = Objects.requireNonNull(
                createdAt,
                "Created date cannot be null"
        );

        this.status = CustomerStatus.ACTIVE;
        this.updatedAt = createdAt;
    }

    public static Customer create(
            String email,
            String firstName,
            String lastName
    ) {
        Instant now = Instant.now();

        return new Customer(
                CustomerId.generate(),
                Email.of(email),
                firstName,
                lastName,
                now
        );
    }

    public void suspend() {
        ensureNotClosed();

        status = CustomerStatus.SUSPENDED;
        touch();
    }

    public void lock() {
        ensureNotClosed();

        status = CustomerStatus.LOCKED;
        touch();
    }

    public void activate() {
        if (status == CustomerStatus.CLOSED) {
            throw new CustomerAlreadyClosedException();
        }

        status = CustomerStatus.ACTIVE;
        touch();
    }

    public void close() {
        if (status == CustomerStatus.CLOSED) {
            throw new IllegalStateException(
                    "Customer is already closed"
            );
        }

        status = CustomerStatus.CLOSED;
        touch();
    }

    private void ensureNotClosed() {
        if (status == CustomerStatus.CLOSED) {
            throw new IllegalStateException(
                    "Closed customer cannot change status"
            );
        }
    }

    private void touch() {
        updatedAt = Instant.now();
    }

    private static String requireText(
            String value,
            String field
    ) {
        Objects.requireNonNull(
                value,
                field + " cannot be null"
        );

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    field + " cannot be blank"
            );
        }

        return value.trim();
    }

    // JavaBean-style getters for REST response DTOs

    public CustomerId getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    // Domain-style accessors

    public CustomerId id() {
        return id;
    }

    public Email email() {
        return email;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public CustomerStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}