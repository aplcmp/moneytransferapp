package com.thecompany.moneytransfer.converters;

import com.thecompany.Datastore;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 *
 */
public class CurrencyRatesConverterTest {
    private static Logger logger = LoggerFactory.getLogger(CurrencyRatesConverterTest.class);
    @Test
    public void testRatesCalc() throws InvalidCurrencyException {
        Datastore datastore = new Datastore("data.xml");
        CurrencyRatesConverter converter = datastore.getConverter();
        Assert.assertEquals(new BigDecimal("0.7"), converter.getRate("USD", "GBP"));
        Assert.assertEquals(new BigDecimal("110"), converter.getRate("USD", "JPY"));

        final Map<String, Integer> ccys = converter.getCcys();
        BigDecimal one = new BigDecimal("1"), zero = new BigDecimal("0");

        logger.info("All rates:");
        ccys.keySet().stream().forEach(ccy1 -> {
            ccys.keySet().stream().forEach(ccy2 -> {
                try {
                    BigDecimal rate = converter.getRate(ccy1, ccy2);
                    logger.info("{}/{}={}", ccy1, ccy2, rate);
                } catch (InvalidCurrencyException e) {
                    e.printStackTrace();
                }
            });
         });

        logger.info("Checking:");
        ccys.keySet().stream().forEach(ccy1 -> {
            ccys.keySet().stream().forEach(ccy2 -> {
                try {
                    BigDecimal rate = converter.getRate(ccy1, ccy2);
                    logger.info("{}/{}={}", ccy1, ccy2, rate);
                    Assert.assertNotSame(zero, rate);
                    if (ccy1.equals(ccy2))
                        Assert.assertEquals(one, rate);
                    BigDecimal vvRate = converter.getRate(ccy2, ccy1);
                    BigDecimal mult = rate.multiply(vvRate);
                    BigDecimal delta = one.subtract(mult);
                    Assert.assertTrue(String.format("%s/%s=%s, <->=%s; mult=%s ", ccy1, ccy2, rate, vvRate, mult),
                    delta.setScale(CurrencyRatesConverter.scale/2, RoundingMode.FLOOR).compareTo(zero.setScale(CurrencyRatesConverter.scale/2))==0);

                } catch (InvalidCurrencyException e) {
                    e.printStackTrace();
                }
            });
         });
    }

}
