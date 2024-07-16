package com.click.account.domain.dto.request;

public record AccountPasswordRequest(
        String account,
        String accountPassword
) {
}
