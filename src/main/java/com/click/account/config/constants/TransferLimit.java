package com.click.account.config.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransferLimit {
    ONETIMELIMIT(1000000L),
    DAILYLIMIT(1000000L);

    private final Long transferLimit;

    public static Long getOnetimeLimit() {
        return TransferLimit.ONETIMELIMIT.getTransferLimit();
    }

    public static Long getDailyLimit() {
        return TransferLimit.DAILYLIMIT.getTransferLimit();
    }
}
