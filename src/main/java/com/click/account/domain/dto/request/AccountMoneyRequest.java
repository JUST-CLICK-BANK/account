package com.click.account.domain.dto.request;

public record AccountMoneyRequest(
        String status,
        String account,
        Long moneyAmount
) {
}
