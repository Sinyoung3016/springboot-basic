package org.prgrms.kdt.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.controller.request.CreateCustomerRequest;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.service.CustomerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {
    private CustomerController customerController;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        this.customerService = mock(CustomerService.class);
        this.customerController = new CustomerController(customerService);
    }

    @Test
    @DisplayName("[성공] 사용자 저장 요청 처리하기")
    void createCustomerTest() {
        String email = "asdf@naver.com";
        Customer customer = new Customer(email);
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(email);
        when(customerService.createCustomer(any())).thenReturn(customer);

        Customer returnedCustomer = customerController.createCustomer(createCustomerRequest);

        Assertions.assertThat(returnedCustomer.getEmail()).isEqualTo(email);
        Assertions.assertThat(returnedCustomer).hasFieldOrProperty("id");
    }

    @Test
    @DisplayName("[실패] 사용자 이메일이 이미 존재할 경우")
    void createCustomerTest_duplicatedUserEmail() {
        String email = "asdf@naver.com";
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(email);
        when(customerService.createCustomer(any())).thenThrow(RuntimeException.class);

        Assertions.assertThatThrownBy(() -> customerController.createCustomer(createCustomerRequest))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("[성공] 이메일을 통해 사용자 가져오는 요청 처리하기")
    void getCustomerByEmail() {
        String email = "asdf@naver.com";
        Customer customer = new Customer(email);
        when(customerService.getCustomerByEmail(email)).thenReturn(customer);

        Customer returnedCustomer = customerController.getCustomerByEmail(email);

        Assertions.assertThat(returnedCustomer.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("[실패] 해당 이메일을 가지는 사용자가 존재하지 않을 경우")
    void getCustomerByEmail_InvalidEmail() {
        String email = "asdf@naver.com";
        when(customerService.getCustomerByEmail(email)).thenThrow(RuntimeException.class);

        Assertions.assertThatThrownBy(() -> customerController.getCustomerByEmail(email))
                .isInstanceOf(RuntimeException.class);
    }
}
