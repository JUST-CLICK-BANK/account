package com.click.account.domain.dto.request.account;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Transfer;

public record TransferRequest(
    String type,
    Long amount,
    Integer transferDate,
    String account
) {
    public Transfer toEntity(Integer type, String myAccount) {
        return Transfer.builder()
            .type(type)
            .amount(amount)
            .transferDate(transferDate)
            .myAccount(myAccount)
            .account(account)
            .build();
    }
}
