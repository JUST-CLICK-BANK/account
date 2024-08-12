package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;

public record AccountAmountResponse(
    Long amount,
    Boolean accountAble
) {
    public static AccountAmountResponse from(Account account) {
        return new AccountAmountResponse(
            account.getMoneyAmount(),
            account.getAccountAble()
        );
    }
}
