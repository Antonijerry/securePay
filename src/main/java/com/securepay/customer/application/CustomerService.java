package com.securepay.customer.application;

import com.securepay.customer.domain.Customer;
import com.securepay.customer.domain.CustomerId;
import com.securepay.customer.domain.Email;
import com.securepay.customer.exception.CustomerAlreadyExistsException;
import com.securepay.customer.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public final class CustomerService {

    private final CustomerRepository customerRepository;

    //constructor injection: here spring sees constructor and says it needs to create customerService
    //and that the customerService needs customerRepository; and that kit has inMemoryCustomerService which implements the customerRepository
    //this is dependency injection
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

    public Customer getCustomer(CustomerId customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}