package com.click.account.domain.dto.request.account;

public record AccountPasswordRequest(
        String account,
        String accountPassword
) {
}
