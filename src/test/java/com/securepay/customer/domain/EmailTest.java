package com.securepay.customer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        Email email = Email.of("ajtechnologies@dev.com");

        assertEquals(
                "ajtechnologies@dev.com",
                email.value()
        );
    }

    @Test
    void shouldTrimEmail() {
        Email email = Email.of(
                "  ajtechnologies@dev.com  "
        );

        assertEquals(
                "ajtechnologies@dev.com",
                email.value()
        );
    }

    @Test
    void shouldRejectInvalidEmail() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Email.of("invalid-email")
        );
    }

    @Test
    void shouldRejectBlankEmail() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Email.of("   ")
        );
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(
                NullPointerException.class,
                () -> Email.of(null)
        );
    }
}