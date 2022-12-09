package org.prgrms.kdt.controller.request;

import org.prgrms.kdt.domain.VoucherType;

public class CreateVoucherRequest {

    private final VoucherType voucherType;
    private final double discountAmount;

    public CreateVoucherRequest(String requestVoucherInfo) {
        String[] request = requestVoucherInfo.split(":");
        if (request.length != 2) {
            throw new RuntimeException("존재하지 않는 바우처 타입입니다");
        }
        this.voucherType = VoucherType.getVoucherTypeByCode(request[0]);
        this.discountAmount = Double.parseDouble(request[1]);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}