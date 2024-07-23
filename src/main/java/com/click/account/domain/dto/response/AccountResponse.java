package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;
import lombok.Builder;

@Builder
public record AccountResponse(
    String account,
    String accountName,
    Long moneyAmount
) {

    public static AccountResponse from(Account account) {
        return AccountResponse.builder()
                .account(account.getAccount())
                .accountName(account.getAccountName())
                .moneyAmount(account.getMoneyAmount())
                .build();
    }
}
