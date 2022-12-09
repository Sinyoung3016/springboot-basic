package org.prgrms.kdt.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.controller.response.VoucherResponse;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.CreateVoucherDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VoucherControllerTest {

    private VoucherController voucherController;
    private VoucherService voucherService;

    @BeforeEach
    void setUp() {
        this.voucherService = mock(VoucherService.class);
        this.voucherController = new VoucherController(voucherService);
    }

    @Test
    @DisplayName("[성공] 바우처 저장 요청 처리하기")
    void createVoucherTest() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        String request = "percent:10";
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(request);
        CreateVoucherDto createVoucherDto = new CreateVoucherDto(voucherType, discountAmount);
        Voucher voucher = new Voucher(voucherType, discountAmount);
        when(voucherService.createVoucher(createVoucherDto)).thenReturn(voucher);

        Voucher savedVoucher = voucherController.createVoucher(createVoucherRequest);

        Assertions.assertThat(savedVoucher).hasFieldOrProperty("id");
        Assertions.assertThat(savedVoucher.getVoucherType()).isEqualTo(voucherType);
    }

    @Test
    @DisplayName("[실패] service에서 바우처 저장이 실패한 경우")
    void createVoucherTest_fail() {
        String request = "percent:10";
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(request);
        when(voucherService.createVoucher(any())).thenThrow(RuntimeException.class);

        Assertions.assertThatThrownBy(() -> voucherController.createVoucher(createVoucherRequest))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("[성공] 모든 바우처 가져오는 요청 처리하기")
    void getAllVoucherTest() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        Voucher voucher = new Voucher(voucherType, discountAmount);
        when(voucherService.getAllVouchers()).thenReturn(List.of(voucher));

        List<VoucherResponse> allVouchers = voucherController.getAllVouchers();

        Assertions.assertThat(allVouchers.size()).isEqualTo(1);
        Assertions.assertThat(allVouchers.get(0)).isInstanceOf(VoucherResponse.class);
    }
}
