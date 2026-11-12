package com.shop.discount_calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AppTest {

    @ParameterizedTest(name = "bill={0}, discount={1}% -> discount={2}, final={3}")
    @DisplayName("Discount and final amount for different bills and percentages")
    @CsvSource({
            "1000,    10,   100.00,  900.00",
            "2500,    20,   500.00,  2000.00",
            "499.99,  15,   75.00,   424.99",
            "999.99,  12.5, 125.00,  874.99",
            "100.50,  10,   10.05,   90.45",
            "0,       50,   0.00,    0.00",
            "1000,    0,    0.00,    1000.00",
            "1000,    100,  1000.00, 0.00",
            "50000,   5,    2500.00,  47500.00"
    })
    void shouldCalculateDiscountAndFinalAmount(String bill, String percent,
                                               String expectedDiscount, String expectedFinal) {
        BigDecimal b = new BigDecimal(bill);
        BigDecimal p = new BigDecimal(percent);

        assertEquals(new BigDecimal(expectedDiscount), App.calculateDiscountAmount(b, p));
        assertEquals(new BigDecimal(expectedFinal), App.calculateFinalAmount(b, p));
    }

    @Test
    @DisplayName("Negative bill is rejected")
    void shouldRejectNegativeBill() {
        assertThrows(IllegalArgumentException.class,
                () -> App.calculateFinalAmount(new BigDecimal("-1"), new BigDecimal("10")));
    }

    @Test
    @DisplayName("Negative discount percentage is rejected")
    void shouldRejectNegativeDiscount() {
        assertThrows(IllegalArgumentException.class,
                () -> App.calculateFinalAmount(new BigDecimal("100"), new BigDecimal("-5")));
    }

    @Test
    @DisplayName("Discount percentage above 100 is rejected")
    void shouldRejectDiscountAbove100() {
        assertThrows(IllegalArgumentException.class,
                () -> App.calculateFinalAmount(new BigDecimal("100"), new BigDecimal("101")));
    }

    @Test
    @DisplayName("Null inputs are rejected")
    void shouldRejectNulls() {
        assertThrows(IllegalArgumentException.class,
                () -> App.calculateDiscountAmount(null, new BigDecimal("10")));
        assertThrows(IllegalArgumentException.class,
                () -> App.calculateDiscountAmount(new BigDecimal("100"), null));
    }
}