package com.click.account.domain.dto.request.account;

import com.click.account.domain.entity.Account;

import com.click.account.domain.entity.User;

public record AccountRequest(
        String accountStatus,
        String accountPassword,
        TransferRequest transferRequest,
        SavingRequest savingRequest
) {
    public Account toEntity(
            String account,
            String accountName,
            User user,
            Boolean accountAble,
            Integer type
    ) {
        return Account.builder()
                .account(account)
                .accountName(accountName)
                .user(user)
                .accountPassword(accountPassword)
                .moneyAmount(0L)
                .accountDailyLimit(1000000L)
                .accountOneTimeLimit(1000000L)
                .accountAble(accountAble)
                .type(type)
                .build();
    }

    public Account toGroupEntity(
            String account,
            String accountName,
            User user,
            String groupAccountCode,
            Boolean accountAble,
            Integer type
    ) {
        return Account.builder()
                .account(account)
                .user(user)
                .accountPassword(accountPassword)
                .accountName(accountName)
                .accountDailyLimit(1000000L)
                .accountOneTimeLimit(1000000L)
                .accountAble(accountAble)
                .groupAccountCode(groupAccountCode)
                .type(type)
                .build();
    }

    public Account toSavingEntity(
        String account,
        String accountName,
        User user,
        Boolean accountAble,
        Integer type
    ) {
        return Account.builder()
            .account(account)
            .accountName(accountName)
            .user(user)
            .accountPassword(accountPassword)
            .accountAble(accountAble)
            .type(type)
            .build();
    }
}
