package com.thecompany.moneytransfer.operations;

/**
 *
 */
public class IllegalMoneyTransferOperationException extends Exception {

    public IllegalMoneyTransferOperationException(String s) {
        super(s);
    }

    public IllegalMoneyTransferOperationException(String s, Exception e) {
        super(s, e);
    }
}
