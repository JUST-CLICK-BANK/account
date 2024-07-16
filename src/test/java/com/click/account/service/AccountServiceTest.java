package com.click.account.service;

import com.click.account.config.utils.GenerateAccount;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountDao accountDao;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUpTest() {
        accountService = new AccountServiceImpl(accountDao, accountRepository);
    }

    @Test
    void 계좌_생성_성공() {
        // given
        UUID userId = UUID.randomUUID();
        AccountRequest request = new AccountRequest("account", "0123", "텅장");
        String generatedAccount = "111222333333";

        Mockito.mockStatic(GenerateAccount.class);
        when(GenerateAccount.generateAccount()).thenReturn(generatedAccount);

        // when
        accountService.saveAccount(userId, request);

        // then
        Mockito.verify(accountDao).compareAccount(generatedAccount);
        Mockito.verify(accountDao).saveAccount(request, generatedAccount, userId);
    }

    @Test
    void 모임_통장_계좌_성공() {
        // given
        UUID userId = UUID.randomUUID();
        AccountRequest request = new AccountRequest("group", "0123", "텅장");
        String generatedAccount = "111222333333";

        Mockito.mockStatic(GenerateAccount.class);
        when(GenerateAccount.generateAccount()).thenReturn(generatedAccount);

        // when
        accountService.saveAccount(userId, request);

        // then
        Mockito.verify(accountDao).compareAccount(generatedAccount);
        Mockito.verify(accountDao).saveGroupAccount(request, generatedAccount, userId);
    }

    @Test
    void 계좌_실패_이미_존재하는_계좌() {
        // given
        UUID userId = UUID.randomUUID();
        AccountRequest request = new AccountRequest("account", "0123", "텅장");
        String generatedAccount = "111222333333";

        Mockito.mockStatic(GenerateAccount.class);
        when(GenerateAccount.generateAccount()).thenReturn(generatedAccount);
        Mockito.doThrow(new IllegalArgumentException("이미 있는 계좌입니다.")).when(accountDao).compareAccount(generatedAccount);

        // when + then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.saveAccount(userId, request);
        });

        Mockito.verify(accountDao).compareAccount(generatedAccount);
        Mockito.verify(accountDao, never()).saveAccount(any(AccountRequest.class), anyString(), any(UUID.class));
        Mockito.verify(accountDao, never()).saveGroupAccount(any(AccountRequest.class), anyString(), any(UUID.class));
    }

    @Test
    void 계좌_삭제_성공() {
        // given
        UUID userId = UUID.randomUUID();
        String account = "111222333333";
        Account existingAccount = mock(Account.class);

        when(accountRepository.findByUserIdAndAccount(userId, account)).thenReturn(Optional.of(existingAccount));

        // when
        accountService.deleteAccount(userId, account);

        // then
        Mockito.verify(accountRepository).findByUserIdAndAccount(userId, account);
        Assertions.assertFalse(existingAccount.getAccountDisable());
    }

    @Test
    void 계좌_삭제_실패_존재하지_않는_계좌() {
        // given
        UUID userId = UUID.randomUUID();
        String account = "111222333333";

        when(accountRepository.findByUserIdAndAccount(userId, account)).thenReturn(Optional.empty());

        // when + then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.deleteAccount(userId, account);
        });

        verify(accountRepository).findByUserIdAndAccount(userId, account);
    }
}