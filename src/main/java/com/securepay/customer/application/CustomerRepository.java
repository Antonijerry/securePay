package com.securepay.customer.application;

import com.securepay.customer.domain.Customer;
import com.securepay.customer.domain.CustomerId;
import com.securepay.customer.domain.Email;

import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(CustomerId id);

    boolean existsByEmail(Email email);
}