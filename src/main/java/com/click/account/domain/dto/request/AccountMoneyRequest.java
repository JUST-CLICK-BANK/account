package com.click.account.domain.dto.request;

public record AccountMoneyRequest(
        String accountStatus,
        String account,
        Long moneyAmount
) {
}
