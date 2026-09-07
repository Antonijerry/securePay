package com.securepay.common.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTest {

    @Test
    void shouldCreateMoney(){
        Money money = Money.of("1000.00", "NGN");

        assertEquals(0, new BigDecimal("1000.00")
                .compareTo(money.amount()));
        assertEquals(
                Currency.getInstance("NGN"),
                money.currency()
        );
    }

    @Test
    void shouldAddSameCurrency(){
        Money first =Money.of("1000.00", "NGN");
        Money second = Money.of("500.00", "NGN");

        Money result = first.add(second);

        assertEquals(
                0,
                new BigDecimal("1500.00")
                        .compareTo(result.amount())
        );
    }

    @Test
    void  ShouldRejectNegativeAmount(){
        assertThrows(
                IllegalArgumentException.class,
                () -> Money.of("-100.00", "NGN")
        );
    }

    @Test
    void shouldRejectCurrencyMismatch(){
        Money naira = Money.of("1000.00", "NGN");
        Money dollars = Money.of("100.00", "USD");

        assertThrows(
                IllegalArgumentException.class,
                () -> naira.add(dollars)
        );
    }

    @Test
    void shouldNotMutateOriginalMoneyWhenAdding(){
        Money original = Money.of("1000.00", "NGN");
        Money additional = Money.of("500.00", "NGN");

        Money result = original.add(additional);

        assertEquals(
                0,
                new BigDecimal("1000.00")
                        .compareTo(original.amount())
        );
        assertEquals(
                0,
                new BigDecimal("1500.00")
                        .compareTo(result.amount())
        );
    }

    @Test
    void shouldMultiplyMoney(){
        Money money = Money.of("1000.00", "NGN");

        Money result = money.multiply(
                new BigDecimal("2")
        );
        assertEquals(
                0,
                new BigDecimal("2000.00")
                        .compareTo(result.amount())
        );
    }

    @Test
    void shouldMultiplyWithRounding(){
        Money money = Money.of("100.00", "NGN");

        Money result = money.multiply(
                new BigDecimal("1.333"),
                2,
                java.math.RoundingMode.HALF_UP
        );
        assertEquals(
                0,
                new BigDecimal("133.30")
                        .compareTo(result.amount())
        );
    }

    @Test
    void shouldRejectNegativeMultiplier(){
        Money money = Money.of("100.00", "NGN");

        assertThrows(
                IllegalArgumentException.class,
                () -> money.multiply(
                        new BigDecimal("-2")
                )
        );
    }
}
