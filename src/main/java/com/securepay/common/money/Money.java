package com.securepay.common.money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import java.math.RoundingMode;

public record Money(
        BigDecimal amount,
        Currency currency
) {
    public Money{
        Objects.requireNonNull(amount, "Amount cannot be null");
        Objects.requireNonNull(currency, "Currency cannot be null");

        if (amount.signum() < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static Money of(String amount, String currencyCode){
        Objects.requireNonNull(amount, "Amount cannot be null");
        Objects.requireNonNull(currencyCode, "Currency Code cannot be null");

        return new Money(
                new BigDecimal(amount),
                Currency.getInstance(currencyCode)
        );
    }

    public Money add(Money other){
        requireSameCurrency(other);

        return  new Money(
                amount.add(other.amount),
                currency
        );
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);

        BigDecimal result = amount.subtract(other.amount);

        if (result.signum() < 0) {
            throw new IllegalArgumentException("Resulting amount cannot be negative");
        };

        return new Money(result, currency);
    }

    public int compareTo(Money other){
        requireSameCurrency(other);

        return  amount.compareTo(other.amount);
    }
    private void requireSameCurrency(Money other){
        Objects.requireNonNull(other, "Money cannot be null");

        if (!currency.equals(other.currency)){
            throw new IllegalArgumentException(
                    "Currency mismatch: "
                    + currency.getCurrencyCode()
                    + " and "
                    + other.currency.getCurrencyCode()
            );
        }
    }

    public Money multiply(BigDecimal multiplier){
        Objects.requireNonNull(multiplier, "Multiplier cannot be null");

        if (multiplier.signum() < 0){
            throw new IllegalArgumentException("Multiplier cannot be negative");
        }

        return new Money(
                amount.multiply(multiplier),
                currency
        );
    }


    public Money multiply(BigDecimal multiplier, int scale, RoundingMode roundingMode){
        Objects.requireNonNull(multiplier, "Multiplier cannot be null");
        Objects.requireNonNull(roundingMode, "Rounding cannot be null");

        if (multiplier.signum() < 0){
            throw new IllegalArgumentException("Multiplier cannot be negative");
        }

        BigDecimal result = amount
                .multiply(multiplier)
                .setScale(scale, roundingMode);

        return new Money(result, currency);
    }
}
