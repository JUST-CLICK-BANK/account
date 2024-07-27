package com.click.account.service;

import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import com.click.account.domain.repository.GroupAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountDao accountDao;

    @Mock
    private GroupAccountDao groupAccountDao;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private GroupAccountRepository groupAccountRepository;

    @Mock
    private GenerateAccount generateAccount;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUpTest() {
        accountService = new AccountServiceImpl(accountDao, groupAccountDao, accountRepository,
            generateAccount);
    }

    @Test
    void 계좌_생성_성공() {
        // given
        TokenInfo tokenInfo = new TokenInfo(
            UUID.randomUUID().toString(),
            "CODE123",
            "asdasd",
            "박박박",
            1
        );
        AccountRequest request = new AccountRequest(
            "account",
            "description",
            "텅장"
        );
        UUID userId = UUID.fromString(tokenInfo.id());
        String account = "416111222";

        when(generateAccount.generateAccount()).thenReturn(account);
        when(accountDao.compareAccount(account)).thenReturn(false);
        doNothing().when(accountDao).saveAccount(request, account, userId);

        // when
        accountService.saveAccount(tokenInfo, request);

        // then
        verify(generateAccount, times(1)).generateAccount();
        verify(accountDao, times(1)).compareAccount(account);
        verify(accountDao, times(1)).saveAccount(request, account, userId);
    }

    @Test
    void 모임_통장_계좌_성공() {
        // given
        UUID userId = UUID.randomUUID();
        AccountRequest request = new AccountRequest("group", "0123", "텅장");
        String generatedAccount = "111222333333";

        Mockito.mockStatic(GenerateAccount.class);
        when(generateAccount.generateAccount()).thenReturn(generatedAccount);

        // when
//        accountService.saveAccount(userId, request);

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
        when(generateAccount.generateAccount()).thenReturn(generatedAccount);
        Mockito.doThrow(new IllegalArgumentException("이미 있는 계좌입니다.")).when(accountDao)
            .compareAccount(generatedAccount);

        // when + then
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            accountService.saveAccount(userId, request);
//        });

        Mockito.verify(accountDao).compareAccount(generatedAccount);
        Mockito.verify(accountDao, never())
            .saveAccount(any(AccountRequest.class), anyString(), any(UUID.class));
        Mockito.verify(accountDao, never())
            .saveGroupAccount(any(AccountRequest.class), anyString(), any(UUID.class));
    }

    @Test
    void 계좌_삭제_성공() {
        // given
        UUID userId = UUID.randomUUID();
        String account = "111222333333";
        Account existingAccount = mock(Account.class);

        // when
        accountService.deleteAccount(userId, account);

        // then
//        Mockito.verify(accountRepository).findByUserIdAndAccount(userId, account);
        Assertions.assertFalse(existingAccount.getAccountDisable());
    }

    @Test
    void 계좌_삭제_실패_존재하지_않는_계좌() {
        // given
        UUID userId = UUID.randomUUID();
        String account = "111222333333";

//        when(accountRepository.findByUserIdAndAccount(userId, account)).thenReturn(Optional.empty());

        // when + then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.deleteAccount(userId, account);
        });

//        verify(accountRepository).findByUserIdAndAccount(userId, account);
    }
}
