package com.click.account.domain.repository;

import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaAuditing
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void 계좌_저장() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "0123",
                "텅장"
        );

        Account saveAccount = accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Assertions.assertEquals(saveAccount.getAccount(), account);
        Assertions.assertEquals(saveAccount.getUserId(), userId);
    }

    @Test
    void 계좌_생성_실패() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "0123",
                "텅장"
        );

        Account saveAccount = accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Assertions.assertNotEquals(saveAccount.getAccount(), "111222333334");
        Assertions.assertNotEquals(saveAccount.getUserId(), UUID.fromString("e700cb76-14ab-403f-9fee-e05c5288abf7"));
    }
}