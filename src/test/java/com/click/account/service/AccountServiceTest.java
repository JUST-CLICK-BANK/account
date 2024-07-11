package com.click.account.service;

import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUpTest() {
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void 계좌_생성_성공() {
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        AccountRequest request = new AccountRequest(
                "0123",
                "텅장"
        );

        when(accountRepository.save(any(Account.class))).then(AdditionalAnswers.returnsFirstArg());
        accountService.saveAccount(userId, request);

        Assertions.assertEquals(request.accountPassword(), "012");

        Mockito.verify(accountRepository).save(any(Account.class));
    }

    @Test
    void 계좌_생성_실패_중복_계좌() {
        UUID userId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        AccountRequest request = new AccountRequest(
                "0123",
                "텅장"
        );
        when(accountRepository.save(any(Account.class))).then(AdditionalAnswers.returnsFirstArg());
        accountService.saveAccount(userId, request);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        Mockito.verify(accountRepository).save(accountCaptor.capture());

        // 캡처된 계좌 객체
        Account savedAccount = accountCaptor.getValue();

        Assertions.assertNotEquals(savedAccount.getAccount(), "012333222222");
    }
}