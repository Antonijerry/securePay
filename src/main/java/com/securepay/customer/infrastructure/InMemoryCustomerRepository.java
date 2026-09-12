package com.securepay.customer.infrastructure;

import com.securepay.customer.application.CustomerRepository;
import com.securepay.customer.domain.Customer;
import com.securepay.customer.domain.CustomerId;
import com.securepay.customer.domain.Email;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<CustomerId, Customer> customers =
            new ConcurrentHashMap<>(); //ConcurrentHashMap provides a thread-safe map implementation suitable for concurrent operations.

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
    public boolean existsByEmail(Email email) {
        return customers.values()
                .stream()
                .anyMatch(customer ->
                        customer.getEmail().equals(email));
    }
}