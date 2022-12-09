package org.prgrms.kdt.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VoucherTypeTest {
    @Test
    @DisplayName("[성공] percent discount 바우처의 할인가격 반환 로직 확인")
    void getSalePrice_withPercentDiscountVoucher() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double price = 1000;
        double discountAmount = 10;
        double salePrice = voucherType.getSalePrice(price, discountAmount);

        Assertions.assertThat(salePrice).isEqualTo(900);
    }

    @Test
    @DisplayName("[성공] fixed amount 바우처의 할인가격 반환 로직 확인")
    void getSalePrice_withFixedAmountVoucher() {
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        double price = 1000;
        double discountAmount = 10;
        double salePrice = voucherType.getSalePrice(price, discountAmount);

        Assertions.assertThat(salePrice).isEqualTo(990);
    }

    @Test
    @DisplayName("[실패] percent discount 바우처의 할인 가격 반환 시, 할인가가 0보다 작으면 예외 발생")
    void getSalePrice_withPercentDiscountVoucher_wrongSalePriceException() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double price = 1000;
        double discountAmount = 200;

        Assertions.assertThatThrownBy(() -> voucherType.getSalePrice(price, discountAmount))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("할인가가 0보다 작습니다.");
    }

    @Test
    @DisplayName("[실패] fixed amount 바우처의 할인 가격 반환 시, 할인가가 0보다 작으면 예외 발생")
    void getSalePrice_withFixedAmountVoucher_wrongSalePriceException() {
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        double price = 1000;
        double discountAmount = 2000;

        Assertions.assertThatThrownBy(() -> voucherType.getSalePrice(price, discountAmount))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("할인가가 0보다 작습니다.");
    }
}
