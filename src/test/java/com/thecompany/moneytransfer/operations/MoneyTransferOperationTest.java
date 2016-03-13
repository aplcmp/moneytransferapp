package com.thecompany.moneytransfer.operations;

import com.thecompany.Datastore;
import com.thecompany.moneytransfer.converters.CurrencyRatesConverter;
import com.thecompany.moneytransfer.dos.Account;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

/**
 *
 */
public class MoneyTransferOperationTest {

    Account a1 = new Account();
    Account a1dup = new Account();
    Account a2 = new Account();
    Account g1 = new Account();

    static Datastore datastore;
    static CurrencyRatesConverter converter;

    @BeforeClass
    public static void beforeClass() {
        datastore = new Datastore("data.xml");
        converter = datastore.getConverter();
    }

    @Before
    public void before() {
        a1.setId("acc1");
        a1.setAmount(new BigDecimal(1000));
        a1.setCurrency("USD");

        a1dup.setId("acc1");
        a1dup.setAmount(new BigDecimal(1000));
        a1dup.setCurrency("USD");

        a2.setId("acc2");
        a2.setAmount(new BigDecimal(1000));
        a2.setCurrency("USD");

        g1.setId("acc3");
        g1.setAmount(new BigDecimal(1000));
        g1.setCurrency("GBP");
    }

    @Test
    public void testTransfer() throws IllegalMoneyTransferOperationException {
        MoneyTransferOperation mto = new MoneyTransferOperation(a1, a2, new BigDecimal(300));
        mto.transfer(converter);
        Assert.assertEquals(0, new BigDecimal("700").compareTo(a1.getAmount()));
        Assert.assertEquals(0, new BigDecimal("1300").compareTo(a2.getAmount()));
    }

    @Test
    public void testTransferConversion() throws IllegalMoneyTransferOperationException {
        MoneyTransferOperation mto = new MoneyTransferOperation(a1, g1, new BigDecimal(300));
        mto.transfer(converter);
        Assert.assertEquals(0, new BigDecimal("700").compareTo(a1.getAmount()));
        Assert.assertEquals(0, new BigDecimal(1000+300*0.7).compareTo(g1.getAmount()));
    }

    @Test(expected = IllegalMoneyTransferOperationException.class)
    public void testTransferSameAcc() throws IllegalMoneyTransferOperationException {
        MoneyTransferOperation mto = new MoneyTransferOperation(a1, a1dup, new BigDecimal(0));
    }

    @Test(expected = IllegalMoneyTransferOperationException.class)
    public void testTransferInsufficient() throws IllegalMoneyTransferOperationException {
        MoneyTransferOperation mto = new MoneyTransferOperation(a1, a2, new BigDecimal(10*1000));
        mto.transfer(converter);
    }

    @Test(expected = IllegalMoneyTransferOperationException.class)
    public void testTransferNegative() throws IllegalMoneyTransferOperationException {
        MoneyTransferOperation mto = new MoneyTransferOperation(a1, a2, new BigDecimal(-500));
        mto.transfer(converter);
    }

    @Test(expected = IllegalMoneyTransferOperationException.class)
    public void testTransferZero() throws IllegalMoneyTransferOperationException {
        MoneyTransferOperation mto = new MoneyTransferOperation(a1, a2, new BigDecimal(0));
        mto.transfer(converter);
    }

}
