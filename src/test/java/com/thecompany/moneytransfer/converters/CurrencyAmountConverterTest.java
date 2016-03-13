package com.thecompany.moneytransfer.converters;

import com.thecompany.moneytransfer.messages.CurrencyAmount;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 *
 */
public class CurrencyAmountConverterTest {
    private static Logger logger = LoggerFactory.getLogger(CurrencyAmountConverterTest.class);

    private void convert(String value, long natural, short fraction) {
        BigDecimal bd1 = new BigDecimal(value);
        CurrencyAmount ca1 = CurrencyAmountConverter.from(bd1);
        Assert.assertEquals(natural, ca1.getValue());
        Assert.assertEquals(fraction, ca1.getPennies());
        Assert.assertEquals(bd1, CurrencyAmountConverter.to(ca1));
    }

    @Test
    public void testConversion() {
        convert("0.99", 0L, (short) 99);
        convert("1234567890.09", 1234567890L, (short) 9);
        convert("1234567890123456789.19", 1234567890123456789L, (short) 19);
        convert("6666.66", 6666L, (short) 66);
    }

    @Test
    public void testDivision() {
        final int count = 1000;
        CurrencyAmount ca = new CurrencyAmount(150L, (short) 29);
        BigDecimal bd = CurrencyAmountConverter.to(ca);
        CurrencyAmount portion = CurrencyAmountConverter.from(bd.divide(new BigDecimal(count)));
        Assert.assertEquals(0, portion.getValue());
        Assert.assertEquals(15, portion.getPennies());
    }
}
