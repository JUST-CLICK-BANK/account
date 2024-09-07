package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;

public record AccountInfoResponse(
    String account,
    Boolean accountAble
) {
    public static AccountInfoResponse from(Account account) {
        return new AccountInfoResponse(
            account.getAccount(),
            account.getAccountAble()
        );
    }
}
