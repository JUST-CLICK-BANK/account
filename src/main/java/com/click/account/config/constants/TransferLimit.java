package com.click.account.config.constants;

public enum TransferLimit {
    ONETIMELIMIT(1000000L),
    DAILYLIMIT(1000000L);

    private final Long transferLimit;

    TransferLimit(Long transferLimit) {
        this.transferLimit = transferLimit;
    }

    public Long getTransferLimit() {
        return transferLimit;
    }
}
