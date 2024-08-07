package com.click.account.config.exception;

public class InsufficientAmountException extends IllegalArgumentException{
    public InsufficientAmountException(Long amount) {
        super(String.valueOf(amount));
    }

}
