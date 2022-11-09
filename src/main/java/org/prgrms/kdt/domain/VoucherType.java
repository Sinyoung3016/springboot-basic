package org.prgrms.kdt.domain;

import java.util.function.BiFunction;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER((price, discountAmount) -> price - discountAmount),
    PERCENT_DISCOUNT_VOUCHER((price, discountAmount) -> price - ((price * discountAmount) / 100));

    private BiFunction<Double, Double, Double> discount;

    VoucherType(BiFunction<Double, Double, Double> discount) {
        this.discount = discount;
    }

    public double getSalePrice(double price, double discountAmount) {
        return discount.apply(price, discountAmount);
    }
}
