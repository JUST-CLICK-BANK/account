package com.click.account.domain.dto.response;

public record AutoTransferAccountResponse(
    String account
) {
    public static AutoTransferAccountResponse from(String account) {
        return new AutoTransferAccountResponse(account);
    }
}
