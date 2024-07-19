package com.click.account.domain.repository;

import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaAuditing
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void 계좌_생성() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
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
                "account",
                "0123",
                "텅장"
        );

        Account saveAccount = accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Assertions.assertNotEquals(saveAccount.getAccount(), "111222333334");
        Assertions.assertNotEquals(saveAccount.getUserId(), UUID.fromString("e700cb76-14ab-403f-9fee-e05c5288abf7"));
    }

    @Test
    void 계좌_찾기_성공() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
                "0123",
                "텅장"
        );

        Account saveAccount = accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Optional<Account> getAccount = accountRepository.findByAccount(saveAccount.getAccount());

        Assertions.assertTrue(getAccount.isPresent());
        Assertions.assertEquals(getAccount.get().getAccount(), saveAccount.getAccount());
    }

    @Test
    void 계좌_찾기_실패() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
                "0123",
                "텅장"
        );

        accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Optional<Account> getAccount = accountRepository.findByAccount("111222333334");

        Assertions.assertFalse(getAccount.isPresent());
    }

    @Test
    void 계좌_유저_계좌_정보_조회_성공() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
                "0123",
                "텅장"
        );

        Account saveAccount = accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Optional<Account> getAccountInfo = accountRepository.findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());

        Assertions.assertTrue(getAccountInfo.isPresent());
        Assertions.assertEquals(getAccountInfo.get().getAccount(), saveAccount.getAccount());
        Assertions.assertEquals(getAccountInfo.get().getUserId(), saveAccount.getUserId());
    }

    @Test
    void 계좌_유저_계좌_정보_조회_실패() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
                "0123",
                "텅장"
        );

        accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Optional<Account> getAccountInfo = accountRepository.findOptionalByUserIdAndAccount(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff865"), "111222333334");

        Assertions.assertFalse(getAccountInfo.isPresent());
    }

    @Test
    void 유저_전체_계좌_조회_성공() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
                "0123",
                "텅장"
        );

        Account saveAccount = accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        List<Account> accounts = accountRepository.findByUserIdAndAccountDisable(saveAccount.getUserId(), saveAccount.getAccountDisable());

        Assertions.assertEquals(accounts.get(0).getUserId(), saveAccount.getUserId());
        Assertions.assertEquals(accounts.get(0).getAccountDisable(), saveAccount.getAccountDisable());
    }

    @Test
    void 유저_전체_계좌_조회_실패() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
                "0123",
                "텅장"
        );

        accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        List<Account> accounts = accountRepository.findByUserIdAndAccountDisable(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff865"), false);

        Assertions.assertTrue(accounts.isEmpty());
    }

    @Test
    void 유저_계좌_정보_조회_성공() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
                "0123",
                "텅장"
        );

        Account saveAccount = accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Optional<Account> accountOptional = accountRepository.findByUserIdAndAccountAndAccountDisable(saveAccount.getUserId(), saveAccount.getAccount(), saveAccount.getAccountDisable());

        Assertions.assertTrue(accountOptional.isPresent());
        Assertions.assertEquals(accountOptional.get().getUserId(), saveAccount.getUserId());
        Assertions.assertEquals(accountOptional.get().getAccount(), saveAccount.getAccount());
        Assertions.assertEquals(accountOptional.get().getAccountDisable(), saveAccount.getAccountDisable());
    }

    @Test
    void 유저_계좌_정보_조회_실패() {
        String account = "111222333333";
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        Long dailyLimit = 1000000L;
        Long onetimeLimit = 1000000L;
        AccountRequest request = new AccountRequest(
                "account",
                "0123",
                "텅장"
        );

        accountRepository.save(request.toEntity(account, userId, dailyLimit, onetimeLimit, true));

        Optional<Account> accountOptional = accountRepository.findByUserIdAndAccountAndAccountDisable(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff865"), "111222333334", false);

        Assertions.assertFalse(accountOptional.isPresent());
    }
}