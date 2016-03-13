package com.thecompany.moneytransfer.operations;

import com.thecompany.moneytransfer.converters.CurrencyRatesConverter;
import com.thecompany.moneytransfer.converters.InvalidCurrencyException;
import com.thecompany.moneytransfer.dos.Account;

import java.math.BigDecimal;

/**
 * Exchange certain currency amount between two accounts
 *
 */
public class MoneyTransferOperation {
    private final static BigDecimal zero = new BigDecimal("0");
    private final Account source;
    private final Account target;
    private final Account first;
    private final Account second;
    private final BigDecimal amount;

    public MoneyTransferOperation(Account source, Account target, BigDecimal amount) throws IllegalMoneyTransferOperationException {
        this.source = source;
        this.target = target;
        this.amount = amount;

        int cmp = source.getId().compareTo(target.getId());

        if (cmp == 0) {
            throw new IllegalMoneyTransferOperationException("Can not transfer money to the same account");
        } else if (cmp < 0 ) {
            first = source;
            second = target;
        } else {
            first = target;
            second = source;
        }

        if (zero.compareTo(amount) >= 0 ) {
            throw new IllegalMoneyTransferOperationException("Can not transfer negative or zero amount");
        }
    }

    public void transfer(CurrencyRatesConverter converter) throws IllegalMoneyTransferOperationException {
        synchronized (first) {
            synchronized (second) {

                BigDecimal srcAmount = amount, trgAmount;

                if (source.getAmount().compareTo(srcAmount) < 0) {
                    throw new IllegalMoneyTransferOperationException("Can not withdraw source account due to insufficient balance");
                }

                if (source.getCurrency().equals(target.getCurrency())) {
                    trgAmount = amount;
                } else {
                    try {
                        trgAmount = srcAmount.multiply(converter.getRate(source.getCurrency(), target.getCurrency()));
                    } catch (InvalidCurrencyException e) {
                        throw new IllegalMoneyTransferOperationException("Conversion can not be performed: ", e);
                    }
                }

                source.setAmount(source.getAmount().subtract(srcAmount));
                target.setAmount(target.getAmount().add(trgAmount));
            }
        }
    }
}
