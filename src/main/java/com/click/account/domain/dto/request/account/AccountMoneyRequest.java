package com.click.account.domain.dto.request.account;

public record AccountMoneyRequest(
        String accountStatus,
        String account,
        String transferAccount,
        Long moneyAmount,
        Long category
) {
}
