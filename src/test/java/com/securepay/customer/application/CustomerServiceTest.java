package com.securepay.customer.application;

import com.securepay.customer.domain.Customer;
import com.securepay.customer.domain.CustomerId;
import com.securepay.customer.exception.CustomerAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @Test
    void shouldCreateCustomer() {

        FakeCustomerRepository repository = new FakeCustomerRepository();
        CustomerService service = new CustomerService(repository);

        CreateCustomerCommand command = new CreateCustomerCommand(
                "ajtechnologies@dev.com",
                "Tony",
                "Jerry"
        );

        CustomerId customerId = service.createCustomer(command);

        assertNotNull(customerId);
        assertEquals(1, repository.customers.size());
    }

    private static class FakeCustomerRepository
            implements CustomerRepository {

        private final Map<CustomerId, Customer> customers = new HashMap<>();

        @Override
        public Customer save(Customer customer) {
            customers.put(customer.getId(), customer);
            return customer;
        }

        @Override
        public Optional<Customer> findById(CustomerId id) {
            return Optional.ofNullable(customers.get(id));
        }

        @Override
        public boolean existsByEmail(
                com.securepay.customer.domain.Email email) {

            return customers.values()
                    .stream()
                    .anyMatch(customer ->
                            customer.getEmail().equals(email));
        }
    }

    @Test
    void shouldRejectDuplicateEmail() {

        FakeCustomerRepository repository = new FakeCustomerRepository();
        CustomerService service = new CustomerService(repository);

        CreateCustomerCommand firstCustomer = new CreateCustomerCommand(
                "ajtechnologies@dev.com",
                "Tony",
                "Jerry"
        );

        CreateCustomerCommand duplicateCustomer = new CreateCustomerCommand(
                "ajtechnologies@dev.com",
                "Jane",
                "Smith"
        );

        service.createCustomer(firstCustomer);

        CustomerAlreadyExistsException exception = assertThrows(
                CustomerAlreadyExistsException.class,
                () -> service.createCustomer(duplicateCustomer)
        );

        assertEquals(
                "Customer with email already exists",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectInvalidEmail() {

        FakeCustomerRepository repository = new FakeCustomerRepository();
        CustomerService service = new CustomerService(repository);

        CreateCustomerCommand command = new CreateCustomerCommand(
                "not-an-email",
                "John",
                "Doe"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createCustomer(command)
        );

        assertEquals(0, repository.customers.size());
    }

}