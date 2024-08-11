package com.click.account.domain.dto.request.account;

import com.click.account.domain.entity.SavingAccount;
import java.time.LocalDate;

public record SavingRequest(
    double interestRate,
    Integer term,
    String product,
    String sendAccount
) {
    public SavingAccount toEntity(String account, LocalDate endAt) {
        return SavingAccount.builder()
            .createAt(LocalDate.now())
            .interestRate(interestRate)
            .endAT(endAt)
            .product(product)
            .myAccount(account)
            .sendAccount(sendAccount)
            .build();
    }
}
