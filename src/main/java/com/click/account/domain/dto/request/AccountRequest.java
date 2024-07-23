package com.click.account.domain.dto.request;

import com.click.account.domain.entity.Account;

import com.click.account.domain.entity.User;

public record AccountRequest(
        String accountStatus,
        String accountPassword
) {
    public Account toEntity(
            String account,
            String accountName,
            User user,
            Long accountDailyLimit,
            Long accountOneTimeLimit,
            boolean accountDisable
    ) {
        return Account.builder()
                .account(account)
                .accountName(accountName)
                .user(user)
                .accountPassword(accountPassword)
                .moneyAmount(0L)
                .accountDailyLimit(accountDailyLimit)
                .accountOneTimeLimit(accountOneTimeLimit)
                .accountDisable(accountDisable)
                .build();
    }

    public Account toGroupEntity(
            String account,
            String accountName,
            User user,
            Long accountDailyLimit,
            Long accountOneTimeLimit,
            String groupAccountCode,
            boolean accountDisable
    ) {
        return Account.builder()
                .account(account)
                .user(user)
                .accountPassword(accountPassword)
                .accountName(accountName)
                .moneyAmount(0L)
                .accountDailyLimit(accountDailyLimit)
                .accountOneTimeLimit(accountOneTimeLimit)
                .accountDisable(accountDisable)
                .groupAccountCode(groupAccountCode)
                .build();
    }
}
