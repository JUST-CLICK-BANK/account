package com.click.account.domain.dto.request;

import com.click.account.domain.entity.Account;

import com.click.account.domain.entity.User;
import java.util.List;
import java.util.UUID;

public record AccountRequest(
        String status,
        String accountPassword,
        String accountName
) {
    public Account toEntity(
            String account,
            User user,
            Long accountDailyLimit,
            Long accountOneTimeLimit,
            boolean accountDisable
    ) {
        return Account.builder()
                .account(account)
                .user(user)
                .accountPassword(accountPassword)
                .accountName(accountName)
                .accountDailyLimit(accountDailyLimit)
                .accountOneTimeLimit(accountOneTimeLimit)
                .accountDisable(accountDisable)
                .build();
    }

    public Account toGroupEntity(
            String account,
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
                .accountDailyLimit(accountDailyLimit)
                .accountOneTimeLimit(accountOneTimeLimit)
                .accountDisable(accountDisable)
                .groupAccountCode(groupAccountCode)
                .build();
    }
}
