package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;
import java.util.List;

public record AccountDetailResponse(
    String account,
    String accountName,
    String groupAccountCode,
    List<UserResponse> userResponses
) {
    public static AccountDetailResponse from(Account account, List<UserResponse> userResponses) {
        return new AccountDetailResponse(
            account.getAccount(),
            account.getAccountName(),
            account.getGroupAccountCode(),
            userResponses
        );
    }

}
