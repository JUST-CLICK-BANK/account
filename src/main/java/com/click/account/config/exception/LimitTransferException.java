package com.click.account.config.exception;

public class LimitTransferException extends IllegalArgumentException {
    public LimitTransferException(Long limit) {
        super(String.valueOf(limit));
    }

}
