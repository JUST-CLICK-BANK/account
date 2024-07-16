package com.click.account.domain.dto.request;

public record AccountTransferLimitRequest(
        String account,
        Long accountDailyLimit,
        Long accountOneTimeLimit
) {
}
