package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Account;

public record AccountUserInfo(
    String userId,
    String account,
    String nickName,
    Long amount
) {
    public static AccountUserInfo from(Account account, String userId) {
        return new AccountUserInfo(
            userId,
            account.getAccount(),
            account.getUser().getUserNickName(),
            account.getMoneyAmount()
        );
    }
}
