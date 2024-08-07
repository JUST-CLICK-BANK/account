package com.click.account.domain.dto.request.account;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Transfer;

public record TransferRequest(
    String type,
    Long amount,
    Integer transferDate
) {
    public Transfer toEntity(Account account, Integer type) {
        return Transfer.builder()
            .type(type)
            .amount(amount)
            .transferDate(transferDate)
            .account(account)
            .build();
    }
}
