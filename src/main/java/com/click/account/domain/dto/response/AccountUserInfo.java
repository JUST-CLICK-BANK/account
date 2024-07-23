package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;

public record AccountUserInfo(
    String userId,
    String account,
    String nickName,
    Long amount
) {
    public static AccountUserInfo from(Account account) {
        return new AccountUserInfo(
            account.getUser().getUserId().toString(),
            account.getAccount(),
            account.getUser().getUserNickName(),
            account.getMoneyAmount()
        );
    }
}
