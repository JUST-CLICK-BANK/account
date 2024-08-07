package com.click.account.domain.dto.request.account;

import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Transfer;
import java.time.LocalDate;

public record SavingAccountReqeust(
    String type,
    Long amount,
    Integer transferDate
) {
    public Transfer toEntity(Account account, Integer type) {
        return Transfer.builder()
            .type(type)
            .amount(amount)
            .transferDate(transferDate)
            .build();
    }
}
