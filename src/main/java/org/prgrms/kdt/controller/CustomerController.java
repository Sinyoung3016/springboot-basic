package org.prgrms.kdt.controller;

import org.prgrms.kdt.controller.request.CreateCustomerRequest;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.dto.CreateCustomerDto;
import org.springframework.stereotype.Component;


@Component
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(createCustomerRequest.email());
        return customerService.createCustomer(createCustomerDto);
    }

    public Customer getCustomerByEmail(String email) {
        return customerService.getCustomerByEmail(email);
    }
}
