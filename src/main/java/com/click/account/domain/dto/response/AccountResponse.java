package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;

public record AccountResponse(
    String account,
    String accountName,
    Long moneyAmount

) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(
            account.getAccountName(),
            account.getAccount(),
            account.getMoneyAmount()
        );

    }

}
