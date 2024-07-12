package com.click.account.domain.dao;

import com.click.account.domain.dto.request.AccountRequest;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        when(accountRepository.findByAccount(generatedAccount)).thenReturn(Optional.of(existingAccount));

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

        Mockito.doThrow(new RuntimeException("Database error")).when(accountRepository).save(any(Account.class));

        // when + then
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
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

        Mockito.doThrow(new RuntimeException("Database error")).when(accountRepository).save(any(Account.class));

        // when + then
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            accountDao.saveGroupAccount(request, account, userId);
        });

        Assertions.assertEquals("Database error", thrown.getMessage());
    }

}