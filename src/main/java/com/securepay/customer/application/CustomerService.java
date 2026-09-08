package com.securepay.customer.application;

import com.securepay.customer.domain.Customer;
import com.securepay.customer.domain.CustomerId;
import com.securepay.customer.domain.Email;
import com.securepay.customer.exception.CustomerAlreadyExistsException;

import java.util.Objects;

public final class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(
                customerRepository,
                "Customer repository cannot be null"
        );
    }

    public CustomerId createCustomer(CreateCustomerCommand command) {

        Objects.requireNonNull(command, "Create customer command cannot be null");

        Email email = Email.of(command.email());

        if (customerRepository.existsByEmail(email)) {
            throw new CustomerAlreadyExistsException();
        }

        Customer customer = Customer.create(
                email.value(),
                command.firstName(),
                command.lastName()
        );

        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer.getId();
    }
}