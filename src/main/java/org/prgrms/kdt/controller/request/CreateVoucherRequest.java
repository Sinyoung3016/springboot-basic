package org.prgrms.kdt.controller.request;

import org.prgrms.kdt.domain.VoucherType;
import org.prgrms.kdt.exception.InvalidRequestException;

public class CreateVoucherRequest {

    private VoucherType voucherType;
    private double discountAmount;

    public CreateVoucherRequest(String requestVoucherInfo) {
        String [] request = requestVoucherInfo.split(":");
        if(request.length < 2) throw new InvalidRequestException();
        this.voucherType = VoucherType.valueOf(request[0]);
        this.discountAmount = Double.parseDouble(request[1]);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}