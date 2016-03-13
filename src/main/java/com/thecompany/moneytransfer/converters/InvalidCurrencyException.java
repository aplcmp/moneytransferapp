package com.thecompany.moneytransfer.converters;

/**
 *
 */
public class InvalidCurrencyException extends Exception {
    public InvalidCurrencyException(String ccy) {
        super("Invalid currency: " + ccy);
    }
}
