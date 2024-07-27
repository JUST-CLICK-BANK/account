package com.click.account.domain.dao;

import com.click.account.domain.dto.request.account.AccountNameRequest;
import com.click.account.domain.dto.request.account.AccountPasswordRequest;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.dto.request.account.AccountTransferLimitRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountDaoTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountDaoImpl accountDao;

    @Test
    void 데이터베이스_계좌_생성된_계좌_중복_비교_실패_체크() {
        // given
        String generatedAccount = "111222333333";
        Account existingAccount = mock(Account.class);

        when(existingAccount.getAccount()).thenReturn(generatedAccount);

        when(accountRepository.findByAccount(generatedAccount)).thenReturn(
            Optional.of(existingAccount));

        // when + then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountDao.compareAccount(generatedAccount);
        });
    }

    @Test
    void 데이터베이스_계좌_생성된_계좌_중복_비교_성공_체크() {
        // given
        String generatedAccount = "111222333333";

        when(accountRepository.findByAccount(generatedAccount)).thenReturn(Optional.empty());

        // when + then
        Assertions.assertDoesNotThrow(() -> {
            accountDao.compareAccount(generatedAccount);
        });
    }

    @Test
    void 일반_계좌_개설_성공() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        AccountRequest request = new AccountRequest(
            "account",
            "0123",
            "텅장"
        );
        String account = "111222333333";

        // when
        accountDao.saveAccount(request, account, userId);

        // then
        Mockito.verify(accountRepository).save(any(Account.class));
    }

    @Test
    void 일반_계좌_개설_실패() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        AccountRequest request = new AccountRequest(
            "account",
            "0123",
            "텅장"
        );
        String account = "111222333333";

        Mockito.doThrow(new RuntimeException("Database error"))
            .when(accountRepository).save(any(Account.class));

        // when, then
        RuntimeException thrown = Assertions.
            assertThrows(
                RuntimeException.class, () -> {
                    accountDao.saveAccount(request, account, userId);
                });

        Assertions.assertEquals("Database error", thrown.getMessage());
    }

    @Test
    void 모임_통장_계좌_개설_성공() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        AccountRequest request = new AccountRequest(
            "group",
            "0123",
            "텅장"
        );
        String account = "111222333333";

        // when
        accountDao.saveGroupAccount(request, account, userId);

        // then
        Mockito.verify(accountRepository).save(any(Account.class));
    }

    @Test
    void 모임_통장_계좌_개설_실패() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        AccountRequest request = new AccountRequest(
            "group",
            "0123",
            "텅장"
        );
        String account = "111222333333";

        Mockito.doThrow(new RuntimeException("Database error")).when(accountRepository)
            .save(any(Account.class));

        // when + then
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            accountDao.saveGroupAccount(request, account, userId);
        });

        Assertions.assertEquals("Database error", thrown.getMessage());
    }

    @Test
    void 유저_계좌_정보_조회_성공() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        AccountRequest request = new AccountRequest(
            "group",
            "0123",
            "텅장"
        );
        String account = "111222333333";

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .accountName(request.accountName())
            .build();

        accountDao.saveGroupAccount(request, account, userId);

        // when
        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.of(saveAccount));

        Account getAccount = accountDao.getAccount(userId, account);

        // then
        assertNotNull(getAccount);
        assertEquals(saveAccount.getAccount(), getAccount.getAccount());
        assertEquals(saveAccount.getUserId(), getAccount.getUserId());
    }

    @Test
    void 유저_계좌_정보_조회_실패() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        AccountRequest request = new AccountRequest(
            "group",
            "0123",
            "텅장"
        );
        String account = "111222333333";

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .accountName(request.accountName())
            .build();

        accountDao.saveGroupAccount(request, account, userId);

        // when
        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            accountDao.getAccount(userId, account);
        });
    }

    @Test
    void 계좌_이름_변경_성공() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String account = "111222333333";
        AccountNameRequest request = new AccountNameRequest(account, "asd");

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .accountName(request.accountName())
            .build();

        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.of(saveAccount));

        // when
        accountDao.updateName(userId, request);

        Mockito.verify(accountRepository, times(1))
            .findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());
        Assertions.assertEquals(saveAccount.getAccountName(), "asd");
    }

    @Test
    void 계좌_이름_변경_실패() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String account = "111222333333";
        AccountNameRequest request = new AccountNameRequest(account, "asd");

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .accountName(request.accountName())
            .build();

        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> accountDao.updateName(userId, request));
        Mockito.verify(accountRepository, times(1))
            .findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());
    }

    @Test
    void 계좌_패스워드_변경_성공() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String account = "111222333333";
        AccountPasswordRequest request = new AccountPasswordRequest(account, "1111");

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .accountPassword(request.accountPassword())
            .build();

        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.of(saveAccount));

        // when
        accountDao.updatePassword(userId, request);

        Mockito.verify(accountRepository, times(1))
            .findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());
        Assertions.assertEquals(saveAccount.getAccountPassword(), "1111");
    }

    @Test
    void 계좌_패스워드_변경_실패() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String account = "111222333333";
        AccountPasswordRequest request = new AccountPasswordRequest(account, "1111");

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .accountPassword(request.accountPassword())
            .build();

        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> accountDao.updatePassword(userId, request));
        Mockito.verify(accountRepository, times(1))
            .findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());
    }

    @Test
    void 계좌_잔액_변경_성공() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String account = "111222333333";
        Long money = 5000L;

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .moneyAmount(money)
            .build();

        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.of(saveAccount));

        // when
        accountDao.updateMoney(userId, account, money);

        Mockito.verify(accountRepository, times(1))
            .findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());
        Assertions.assertEquals(saveAccount.getAccountPassword(), "1111");
    }

    @Test
    void 계좌_잔액_변경_실패() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String account = "111222333333";
        Long money = 5000L;

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .moneyAmount(money)
            .build();

        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> accountDao.updateMoney(userId, account, money));
        Mockito.verify(accountRepository, times(1))
            .findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());
    }

    @Test
    void 계좌_한도_변경_성공() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String account = "111222333333";
        Long dailyLimit = 2000000L;
        Long onetimeLimit = 2000000L;
        AccountTransferLimitRequest request = new AccountTransferLimitRequest(account, dailyLimit,
            onetimeLimit);

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .accountDailyLimit(dailyLimit)
            .accountOneTimeLimit(onetimeLimit)
            .build();

        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.of(saveAccount));

        // when
        accountDao.updateAccountLimit(userId, request);

        Mockito.verify(accountRepository, times(1))
            .findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());
        Assertions.assertEquals(saveAccount.getAccountDailyLimit(), dailyLimit);
        Assertions.assertEquals(saveAccount.getAccountOneTimeLimit(), onetimeLimit);
    }

    @Test
    void 계좌_한도_변경_실패() {
        // given
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        String account = "111222333333";
        Long dailyLimit = 2000000L;
        Long onetimeLimit = 2000000L;
        AccountTransferLimitRequest request = new AccountTransferLimitRequest(account, dailyLimit,
            onetimeLimit);

        Account saveAccount = Account.builder()
            .account(account)
            .userId(userId)
            .accountDailyLimit(dailyLimit)
            .accountOneTimeLimit(onetimeLimit)
            .build();

        Mockito.when(accountRepository.findOptionalByUserIdAndAccount(userId, account))
            .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> accountDao.updateAccountLimit(userId, request));
        Mockito.verify(accountRepository, times(1))
            .findOptionalByUserIdAndAccount(saveAccount.getUserId(), saveAccount.getAccount());
    }

}