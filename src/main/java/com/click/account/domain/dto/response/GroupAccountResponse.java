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
    Boolean accountAble

) {
    public static GroupAccountResponse from(Account account, GroupAccountMember groupAccount) {
        return GroupAccountResponse.builder()
                .account(account.getAccount())
                .accountName(account.getAccountName())
                .moneyAmount(account.getMoneyAmount())
                .accountAble(account.getAccountAble())
                .build();

    }

}
