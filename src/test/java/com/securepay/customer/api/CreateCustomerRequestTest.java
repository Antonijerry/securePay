package com.securepay.customer.api;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateCustomerRequestTest {

    private final Validator validator;

    CreateCustomerRequestTest() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @Test
    void shouldRejectInvalidCustomerRequest() {

        CreateCustomerRequest request =
                new CreateCustomerRequest(
                        "bad-email",
                        "",
                        ""
                );

        var violations = validator.validate(request);

        assertEquals(3, violations.size());
    }

    @Test
    void shouldAcceptValidCustomerRequest() {

        CreateCustomerRequest request =
                new CreateCustomerRequest(
                        "john@example.com",
                        "John",
                        "Doe"
                );

        var violations = validator.validate(request);

        assertEquals(0, violations.size());
    }
}