package com.click.account.domain.dto.request.account;

public record AccountMoneyRequest(
        String accountStatus,
        String account,
        Long moneyAmount,
        Integer category
) {
}
