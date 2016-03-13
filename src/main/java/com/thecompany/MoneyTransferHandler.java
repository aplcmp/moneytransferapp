package com.thecompany;


import com.thecompany.moneytransfer.MoneyTransfer;
import com.thecompany.moneytransfer.converters.CurrencyAmountConverter;
import com.thecompany.moneytransfer.converters.CurrencyRatesConverter;
import com.thecompany.moneytransfer.dos.Account;
import com.thecompany.moneytransfer.messages.*;
import com.thecompany.moneytransfer.operations.IllegalMoneyTransferOperationException;
import com.thecompany.moneytransfer.operations.MoneyTransferOperation;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Handle money transfer requests
 */
public class MoneyTransferHandler implements MoneyTransfer.Iface {
    private static Logger logger = LoggerFactory.getLogger(MoneyTransferHandler.class);

    private final Datastore datastore;
    private final CurrencyRatesConverter converter;

    public MoneyTransferHandler(Datastore datastore) {
        this.datastore = datastore;
        this.converter = datastore.getConverter();
    }

    @Override
    public CurrencyTransferResponse transfer(CurrencyTransferRequest request) throws TException {
        logger.info("Moving {} from {} to {}", new Object[] { request.getAmount(), request.getSourceAccountId(), request.getTargetAccountId()} );
        final CurrencyTransferResponse res = new CurrencyTransferResponse();

        final String srcId = request.getSourceAccountId();
        final String trgId = request.getTargetAccountId();

        final Account srcAcc = datastore.getAccountMap().get(srcId);
        final Account trgAcc = datastore.getAccountMap().get(trgId);

        if (srcAcc == null) {
            res.setDescription("Source account is not found");
            res.setStatus(CurrencyTransferResult.FAIL);
            return res;
        }

        if (trgAcc == null) {
            res.setDescription("Target account is not found");
            res.setStatus(CurrencyTransferResult.FAIL);
            return res;
        }

        final BigDecimal amount = CurrencyAmountConverter.to(request.getAmount());

        try {
            MoneyTransferOperation mto = new MoneyTransferOperation(srcAcc, trgAcc, amount);
            mto.transfer(converter);
        } catch (IllegalMoneyTransferOperationException imto) {
            res.setDescription(imto.getMessage());
            res.setStatus(CurrencyTransferResult.FAIL);
            return res;
        }

        res.setStatus(CurrencyTransferResult.OK);
        logger.info("Moved {} from {} to {}", new Object[] { request.getAmount(), request.getSourceAccountId(), request.getTargetAccountId()} );
        return res;
    }

    @Override
    public CurrencyAmount getBalance(String accountId) throws IllegalOperation, TException {
        logger.info("Retrieving balance for {}", accountId);
        final Account srcAcc = datastore.getAccountMap().get(accountId);
        if (srcAcc == null)
            throw new IllegalOperation("Source account is not found");

        final BigDecimal balance;
        synchronized (srcAcc) {
            balance = srcAcc.getAmount();
        }
        return new CurrencyAmountConverter().from(balance);
    }
}
