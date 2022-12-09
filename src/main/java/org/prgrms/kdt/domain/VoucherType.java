package org.prgrms.kdt.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;

import static java.util.stream.Collectors.toMap;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("fixed", (price, discountAmount) -> {
        double salePrice = price - discountAmount;
        if (salePrice < 0) {
            throw new RuntimeException("할인가가 0보다 작습니다.");
        }
        return salePrice;
    }),
    PERCENT_DISCOUNT_VOUCHER("percent", (price, discountAmount) -> {
        double salePrice = price - ((price * discountAmount) / 100);
        if (salePrice < 0) {
            throw new RuntimeException("할인가가 0보다 작습니다.");
        }
        return salePrice;
    });

    private final String code;

    private final DoubleBinaryOperator discount;

    VoucherType(String code, DoubleBinaryOperator discount) {
        this.discount = discount;
        this.code = code;
    }

    private static final Map<String, VoucherType> VOUCHERS =
            Arrays.stream(VoucherType.values())
                    .collect(toMap(VoucherType::getCode, discountVoucherType -> discountVoucherType));

    private String getCode() {
        return code;
    }

    public double getSalePrice(double price, double discountAmount) {
        return discount.applyAsDouble(price, discountAmount);
    }

    public static VoucherType getVoucherTypeByCode(String code) {
        return Optional.ofNullable(VOUCHERS.get(code))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 바우처 타입입니다"));
    }
}
