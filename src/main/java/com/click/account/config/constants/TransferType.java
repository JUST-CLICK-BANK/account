package com.click.account.config.constants;

public enum TransferType {
    CARD(1),
    ACCOUNT(2),
    SAVING(3);

    private final Integer transferType;

    TransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public Integer getTransferType() {
        return transferType;
    }
}
