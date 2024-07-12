package com.click.account.domain.dto.request;

import com.click.account.domain.entity.Account;

import java.util.UUID;

public record AccountRequest(
        String status,
        String accountPassword,
        String accountName
) {
    public Account toEntity(
            String account,
            UUID userId,
            Long accountDailyLimit,
            Long accountOneTimeLimit,
            boolean accountDisable
    ) {
        return Account.builder()
                .account(account)
                .userId(userId)
                .accountPassword(accountPassword)
                .accountName(accountName)
                .accountDailyLimit(accountDailyLimit)
                .accountOneTimeLimit(accountOneTimeLimit)
                .accountDisable(accountDisable)
                .build();
    }

    public Account toGroupEntity(
            String account,
            UUID userId,
            Long accountDailyLimit,
            Long accountOneTimeLimit,
            String groupAccountCode,
            boolean accountDisable
    ) {
        return Account.builder()
                .account(account)
                .userId(userId)
                .accountPassword(accountPassword)
                .accountName(accountName)
                .accountDailyLimit(accountDailyLimit)
                .accountOneTimeLimit(accountOneTimeLimit)
                .accountDisable(accountDisable)
                .groupAccountCode(groupAccountCode)
                .build();
    }
}
