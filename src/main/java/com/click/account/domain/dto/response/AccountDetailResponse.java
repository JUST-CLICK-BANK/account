package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;
import java.util.List;

public record AccountDetailResponse(
    String account,
    String accountName,
    String groupAccountCode,
    List<GroupAccountMemberResponse> groupAccountMemberResponses
) {
    public static AccountDetailResponse from(Account account, List<GroupAccountMemberResponse> res) {
        return new AccountDetailResponse(
            account.getAccount(),
            account.getAccountName(),
            account.getGroupAccountCode(),
            res
        );
    }

}
