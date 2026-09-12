package com.securepay.customer.api;

import com.securepay.customer.application.CreateCustomerCommand;
import com.securepay.customer.application.CustomerService;
import com.securepay.customer.domain.CustomerId;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    //constructor injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerId createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {

        CreateCustomerCommand command = new CreateCustomerCommand(
                request.email(),
                request.firstName(),
                request.lastName()
        );

        return customerService.createCustomer(command);
    }
}