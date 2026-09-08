package com.securepay.customer.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            );

    public Email {
        Objects.requireNonNull(
                value,
                "Email cannot be null"
        );

        value = value.trim();

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    "Email cannot be blank"
            );
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "Invalid email format"
            );
        }
    }

    public static Email of(String value) {
        return new Email(value);
    }
}