package com.click.account.domain.dto.request;

public record AccountTransferLimitRequest(
        Long accountDailyLimit,
        Long accountOneTimeLimit
) {
}
