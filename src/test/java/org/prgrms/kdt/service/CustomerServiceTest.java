package org.prgrms.kdt.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.repository.CustomerRepository;
import org.prgrms.kdt.service.dto.CreateCustomerDto;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        this.customerRepository = mock(CustomerRepository.class);
        this.customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("[성공] 유저 저장하기")
    void createCustomerTest() {
        String email = "asdf@naver.com";
        Customer customer = new Customer(email);
        when(customerRepository.saveCustomer(any())).thenReturn(Optional.of(customer));
        CreateCustomerDto dto = new CreateCustomerDto(email);

        Customer returnedCustomer = customerService.createCustomer(dto);

        Assertions.assertThat(returnedCustomer.getEmail()).isEqualTo(email);
        Assertions.assertThat(returnedCustomer).hasFieldOrProperty("id");
    }

    @Test
    @DisplayName("[실패] 유저 저장 실패한 경우")
    void createCustomerTest_fail() {
        String email = "asdf@naver.com";
        Customer customer = new Customer(email);
        when(customerRepository.getCustomerByEmail(email)).thenReturn(Optional.of(customer));
        CreateCustomerDto dto = new CreateCustomerDto(email);

        Assertions.assertThatThrownBy(() -> customerService.createCustomer(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Duplicated Customer exist");
    }

    @Test
    @DisplayName("[성공] id를 통해 유저 가져오기")
    void getCustomerByIdTest() {
        String email = "asdf@naver.com";
        Customer customer = new Customer(email);

        long customerId = 0;
        when(customerRepository.getCustomerById(customerId)).thenReturn(Optional.of(customer));

        Customer customerById = customerService.getCustomerById(customerId);

        Assertions.assertThat(customerById.getId()).isEqualTo(customerId);
        Assertions.assertThat(customerById.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("[실패] 존재하지 않는 id를 통해 유저 가져오는 경우")
    void getCustomerByIdTest_fail() {
        long customerId = 0;
        when(customerRepository.getCustomerById(customerId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> customerService.getCustomerById(customerId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Can't find Customer By Id");
    }

    @Test
    @DisplayName("[성공] email을 통해 유저 가져오기")
    void getCustomerByEmailTest() {
        String email = "asdf@naver.com";
        Customer customer = new Customer(email);
        when(customerRepository.getCustomerByEmail(email)).thenReturn(Optional.of(customer));

        Customer customerByEmail = customerService.getCustomerByEmail(email);

        Assertions.assertThat(customerByEmail.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("[실패] 존재하지 않는 email을 통해 유저 가져오는 경우")
    void getCustomerByEmailTest_fail() {
        String email = "asdf@naver.com";
        when(customerRepository.getCustomerByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> customerService.getCustomerByEmail(email))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Can't find Customer By Email");
    }
}
