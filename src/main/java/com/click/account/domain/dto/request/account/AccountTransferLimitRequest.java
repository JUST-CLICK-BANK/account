package com.click.account.domain.dto.request.account;

public record AccountTransferLimitRequest(
        String account,
        Long accountDailyLimit,
        Long accountOneTimeLimit
) {
}
