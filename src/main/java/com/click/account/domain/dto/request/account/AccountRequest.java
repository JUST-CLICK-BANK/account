package com.click.account.domain.dto.request.account;

import com.click.account.domain.entity.Account;

import com.click.account.domain.entity.User;

public record AccountRequest(
        String accountStatus,
        String accountPassword,
        SavingAccountReqeust savingAccountReqeust
) {
    public Account toEntity(
            String account,
            String accountName,
            User user,
            boolean accountDisable,
            Integer type
    ) {
        return Account.builder()
                .account(account)
                .accountName(accountName)
                .user(user)
                .accountPassword(accountPassword)
                .moneyAmount(10000000L)
                .accountDailyLimit(1000000L)
                .accountOneTimeLimit(1000000L)
                .accountDisable(accountDisable)
                .type(type)
                .build();
    }

    public Account toGroupEntity(
            String account,
            String accountName,
            User user,
            String groupAccountCode,
            boolean accountDisable,
            Integer type
    ) {
        return Account.builder()
                .account(account)
                .user(user)
                .accountPassword(accountPassword)
                .accountName(accountName)
                .accountDailyLimit(1000000L)
                .accountOneTimeLimit(1000000L)
                .accountDisable(accountDisable)
                .groupAccountCode(groupAccountCode)
                .type(type)
                .build();
    }

    public Account toSavingEntity(
        String account,
        String accountName,
        User user,
        boolean accountDisable,
        Integer type
    ) {
        return Account.builder()
            .account(account)
            .accountName(accountName)
            .user(user)
            .accountPassword(accountPassword)
            .accountDisable(accountDisable)
            .type(type)
            .build();
    }
}
