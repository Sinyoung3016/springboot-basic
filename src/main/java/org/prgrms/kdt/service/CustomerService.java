package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.service.dto.CreateCustomerDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CreateCustomerDto createCustomerDto) {
        Customer newCustomer = new Customer(createCustomerDto.email());
        if (hasDuplicatedCustomer(createCustomerDto.email())) {
            throw new RuntimeException("Duplicated Customer exist");
        }
        Optional<Customer> returnedCustomer = customerRepository.saveCustomer(newCustomer);
        return returnedCustomer.orElseThrow(RuntimeException::new);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email)
                .orElseThrow(() -> new RuntimeException("Can't find Customer By Email"));
    }

    public Customer getCustomerById(long customerId) {
        return customerRepository.getCustomerById(customerId)
                .orElseThrow(() -> new RuntimeException("Can't find Customer By Id"));
    }

    public boolean hasDuplicatedCustomer(String email) {
        return customerRepository.getCustomerByEmail(email).isPresent();
    }
}
