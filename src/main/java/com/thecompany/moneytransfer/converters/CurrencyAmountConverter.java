package com.thecompany.moneytransfer.converters;

import com.thecompany.moneytransfer.messages.CurrencyAmount;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 */
public class CurrencyAmountConverter {
    static final BigDecimal CURRENCY_PENNY_AMOUNT = new BigDecimal(100); // may change for different currencies
    static final int scale = 2;

    public static CurrencyAmount from(BigDecimal amount) {
        BigDecimal fraction = amount.remainder(BigDecimal.ONE);
        BigDecimal natural = amount.subtract(fraction);
        CurrencyAmount res = new CurrencyAmount(natural.longValue(), fraction.multiply(CURRENCY_PENNY_AMOUNT).shortValue());
        return res;
    }

    public static BigDecimal to(CurrencyAmount amount) {
        BigDecimal res = new BigDecimal(amount.getValue());
        BigDecimal fraction = new BigDecimal(amount.getPennies()).divide(CURRENCY_PENNY_AMOUNT, scale, RoundingMode.UNNECESSARY);
        res = res.add(fraction);
        return res;
    }
}
