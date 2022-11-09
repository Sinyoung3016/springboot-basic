package org.prgrms.kdt.controller;

import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.CreateVoucherDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Component
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public String requestVoucherInfo() {
        return "> 바우처 타입와 할인 가격을 선택해주세요 : ";
    }

    public String create(CreateVoucherRequest createVoucherRequest) {
        CreateVoucherDto createVoucherDto = new CreateVoucherDto(createVoucherRequest);
        if (voucherService.createVoucher(createVoucherDto))
            return "응 저장";
        return "응 안저장";
    }

    public String list() {
        StringBuilder rtn = new StringBuilder();
        List<Voucher> vouchers = voucherService.getAllVouchers();
        if (vouchers.isEmpty()) return "없을 무";
        for (Voucher voucher : vouchers) {
            rtn.append(voucher).append("\n");
        }
        return rtn.toString();
    }

    public String exit() {
        return "> 시스템을 종료합니다";
    }
}
