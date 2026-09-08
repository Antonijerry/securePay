package com.securepay.customer.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateActiveCustomer() {
        Customer customer = Customer.create(
                "ajtechnologies@dev.com",
                "Tony",
                "Jerry"
        );

        assertNotNull(customer.id());
        assertEquals("ajtechnologies@dev.com", customer.email());
        assertEquals("Tony", customer.firstName());
        assertEquals("Jerry", customer.lastName());
        assertEquals(CustomerStatus.ACTIVE, customer.status());
        assertNotNull(customer.createdAt());
        assertNotNull(customer.updatedAt());
    }

    @Test
    void shouldSuspendCustomer() {
        Customer customer = Customer.create(
                "ajtechnologies@dev.com",
                "Tony",
                "Jerry"
        );

        customer.suspend();

        assertEquals(
                CustomerStatus.SUSPENDED,
                customer.status()
        );
    }

    @Test
    void shouldLockCustomer() {
        Customer customer = Customer.create(
                "ajtechnologies@dev.com",
                "Tony",
                "Jerry"
        );

        customer.lock();

        assertEquals(
                CustomerStatus.LOCKED,
                customer.status()
        );
    }

    @Test
    void shouldCloseCustomer() {
        Customer customer = Customer.create(
                "ajtechnologies@dev.com",
                "Tony",
                "Jerry"
        );

        customer.close();

        assertEquals(
                CustomerStatus.CLOSED,
                customer.status()
        );
    }

    @Test
    void shouldNotReactivateClosedCustomer() {
        Customer customer = Customer.create(
                "ajtechnologies@dev.com",
                "Tony",
                "Jerry"
        );

        customer.close();

        assertThrows(
                IllegalStateException.class,
                customer::activate
        );
    }

    @Test
    void shouldRejectBlankEmail() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Customer.create(
                        "",
                        "Tony",
                        "Jerry"
                )
        );
    }

    @Test
    void shouldRejectNullEmail() {
        assertThrows(
                NullPointerException.class,
                () -> Customer.create(
                        null,
                        "Tony",
                        "Jerry"
                )
        );
    }
}