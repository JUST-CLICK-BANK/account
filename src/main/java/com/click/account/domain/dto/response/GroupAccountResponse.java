package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import lombok.Builder;

@Builder
public record GroupAccountResponse(
    String account,
    String accountName,
    Long moneyAmount,
    String userProfileImg,
    String userNickname,
    Boolean accountDisable

) {
    public static GroupAccountResponse from(Account account, GroupAccountMember groupAccount) {
        return GroupAccountResponse.builder()
                .account(account.getAccount())
                .accountName(account.getAccountName())
                .moneyAmount(account.getMoneyAmount())
                .accountDisable(account.getAccountDisable())
                .userProfileImg(groupAccount != null ? groupAccount.getUserPofileImg() : null)
                .userNickname(groupAccount != null ? groupAccount.getUserNickName() : null)
                .build();

    }

}
