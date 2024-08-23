package com.click.account.domain.dto.request.account;

public record AccountMoneyRequest(
        String accountStatus,
        String account,
        String nickname,
        Long moneyAmount,
        Integer category,
        Long cardId
) {
}
