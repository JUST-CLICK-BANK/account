package com.click.account.service;

import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.dto.request.account.AccountNameRequest;
import com.click.account.domain.dto.request.account.AccountPasswordRequest;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.dto.request.account.AccountTransferLimitRequest;
import com.click.account.domain.dto.response.AccountDetailResponse;
import com.click.account.domain.dto.response.AccountUserInfo;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;
import com.click.account.domain.repository.AccountRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private UserService userService;

    @Mock
    private ApiService apiService;

    @Mock
    private AccountDao accountDao;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private FriendService friendService;

    @Mock
    private GroupAccountDao groupAccountDao;

    @Mock
    private GroupAccountMemberService groupAccountMemberService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private TokenInfo tokenInfo;

    @BeforeEach
    public void setUpTest() {
        accountService = new AccountServiceImpl(
            accountDao,
            groupAccountDao,
            accountRepository,
            apiService,
            userService,
            friendService,
            groupAccountMemberService
        );
        tokenInfo = new TokenInfo(
            "bfa974e2-817c-4da3-bd46-d841ffb7b17a",
            "BWTUI2",
            "",
            "박박박",
                6
        );
    }

    @Test
    void 계좌_생성_성공() {
        // given
        User user = new User();
        UUID userId = UUID.fromString(tokenInfo.id());
        String account = "416111222";
        AccountRequest req = new AccountRequest(
            "account",
            "1111"
        );

        MockedStatic<GenerateAccount> mockedStatic = Mockito.mockStatic(GenerateAccount.class);
        when(GenerateAccount.generateAccount()).thenReturn(account);
        when(userService.getUser(any(TokenInfo.class))).thenReturn(user);
        when(accountDao.compareAccount(account)).thenReturn(false);
        doNothing().when(accountDao).saveAccount(req, account, user);

        // when
        accountService.saveAccount(tokenInfo, req);

        // then
        mockedStatic.verify(GenerateAccount::generateAccount, times(1));
        verify(accountDao, times(1)).compareAccount(account);
        verify(accountDao, times(1)).saveAccount(req, account, user);
    }

    @Test
    void 모임_통장_계좌_성공() {
        // given
        User user = new User();
        AccountRequest req = new AccountRequest("group", "0123");
        String account = "111222333333";

        MockedStatic<GenerateAccount> mockedStatic = Mockito.mockStatic(GenerateAccount.class);
        when(GenerateAccount.generateAccount()).thenReturn(account);
        when(userService.getUser(any(TokenInfo.class))).thenReturn(user);
        when(accountDao.compareAccount(anyString())).thenReturn(false);
        doNothing().when(accountDao).saveGroupAccount(req, account, user);

        // when
        accountService.saveAccount(tokenInfo, req);

        // then
        mockedStatic.verify(GenerateAccount::generateAccount, times(1));
        verify(accountDao, times(1)).saveGroupAccount(any(AccountRequest.class), anyString(), any(User.class));
        verify(groupAccountDao, times(1)).saveGroupToUser(any(TokenInfo.class), anyString(), any(UUID.class));
    }

    @Test
    void 계좌_실패_이미_존재하는_계좌() {
        // given
        String generatedAccount = "111222333333";
        AccountRequest req = new AccountRequest(
            "account",
            "1111"
        );
        Mockito.mockStatic(GenerateAccount.class);
        when(GenerateAccount.generateAccount()).thenReturn(generatedAccount);
        Mockito.doThrow(new IllegalArgumentException("이미 있는 계좌입니다.")).when(accountDao)
            .compareAccount(generatedAccount);

        // when + then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.saveAccount(tokenInfo, req);
        });

        Mockito.verify(accountDao).compareAccount(generatedAccount);
        Mockito.verify(accountDao, never())
            .saveAccount(any(AccountRequest.class), anyString(), any(User.class));
        Mockito.verify(accountDao, never())
            .saveGroupAccount(any(AccountRequest.class), anyString(), any(User.class));
    }

    @Test
    void 유저_전체_계좌_읽기() {
        // given
        UUID userId = UUID.randomUUID();
        when(accountRepository.findAccounts(userId, true)).thenReturn(Collections.emptyList());

        // when
        List<UserAccountResponse> result = accountService.findUserAccountByUserIdAndAccount(userId, tokenInfo);

        // then
        Assertions.assertTrue(result.isEmpty());
        verify(accountRepository, times(1)).findAccounts(userId, true);
    }

    @Test
    void 계좌_없음() {
        // given
        UUID userId = UUID.randomUUID();
        List<Account> disabledAccounts = List.of(new Account());
        when(accountRepository.findAccounts(userId, true)).thenReturn(disabledAccounts);

        // when
        List<UserAccountResponse> result = accountService.findUserAccountByUserIdAndAccount(userId, tokenInfo);

        // then
        Assertions.assertFalse(result.isEmpty());
        verify(accountRepository, times(1)).findAccounts(userId, true);
    }

    @Test
    void 계좌_정보_읽기() {
        // given
        String requestAccount = "account123";
        User user = new User(
            UUID.randomUUID(),
            null,
            null,
            null,
            null,
            null
        );
        Account account = new Account(
            requestAccount,
            null,
            user,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        when(accountDao.getAccount(requestAccount)).thenReturn(account);

        // when
        AccountUserInfo result = accountService.getAccountFromUserId(requestAccount, tokenInfo);

        // then
        Assertions.assertNotNull(result);
        verify(accountDao, times(1)).getAccount(requestAccount);
        Assertions.assertEquals(user.getUserId().toString(), result.userId());
    }

    @Test
    void tokenNotValid() {
        // given
        String requestAccount = "account123";

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.getAccountFromUserId(requestAccount, null));
    }

    @Test
    void 모임_통장_정보_읽기() {
        // given
        String reqAccount = "account123";
        Account account = new Account();
        List<GroupAccountMemberResponse> groupAccountMemberResponses = List.of(new GroupAccountMemberResponse(
            tokenInfo.name(),
            tokenInfo.img(),
            tokenInfo.code(),
            true
        ));
        when(accountDao.getAccount(reqAccount)).thenReturn(account);
        when(groupAccountMemberService.getGroupAccountMember(account)).thenReturn(groupAccountMemberResponses);

        // when
        AccountDetailResponse result = accountService.getAccountInfo(tokenInfo, reqAccount);

        // then
        Assertions.assertNotNull(result);
        verify(accountDao, times(1)).getAccount(reqAccount);
        verify(groupAccountMemberService, times(1)).getGroupAccountMember(account);
    }

    @Test
    void accountNotExist() {
        // then
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> accountService.getAccountInfo(tokenInfo, null)
        );
    }

    @Test
    void 게좌_이름_변경_성공() {
        // given
        UUID userId = UUID.fromString(tokenInfo.id());
        Account account = mock(Account.class);
        AccountNameRequest request = new AccountNameRequest(account.getAccount(), "asd");

        when(accountDao.getAccount(account.getAccount())).thenReturn(account);

        // when
        accountService.updateName(userId, request);

        // then
        verify(accountDao, times(1)).getAccount(request.account());
        verify(account, times(1)).updateName(request.accountName());
    }

    @Test
    void accountNameNotValidToken() {
        // given
        AccountNameRequest request = new AccountNameRequest("account123", "newAccountName");

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.updateName(null, request));
    }

    @Test
    void 게좌_비밀번호_변경_성공() {
        // given
        UUID userId = UUID.fromString(tokenInfo.id());
        Account account = mock(Account.class);
        AccountPasswordRequest request = new AccountPasswordRequest(account.getAccount(), "1111");

        when(accountDao.getAccount(account.getAccount())).thenReturn(account);

        // when
        accountService.updatePassword(userId, request);

        // then
        verify(accountDao, times(1)).getAccount(request.account());
        verify(account, times(1)).updateName(request.accountPassword());
    }

    @Test
    void accountPasswordNotValidToken() {
        // given
        AccountPasswordRequest request = new AccountPasswordRequest("account123", "1111");

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.updatePassword(null, request));
    }

    @Test
    void 입금_성공() {
        // given
        UUID userId = UUID.fromString(tokenInfo.id());
        Account account = new Account();
        account.updateMoney(1000L);
        account.updateTransferLimit(10000L, 1000000L);
        String transAccount = "111222333333";
        AccountMoneyRequest req = new AccountMoneyRequest(
            "deposit",
            "222333444444",
            transAccount,
            5000L,
            1L
        );

        when(accountDao.getAccount(req.account())).thenReturn(account);

        //when
        accountService.updateMoney(userId, req);

        // then
        verify(accountDao, times(1)).getAccount(req.account());
        Assertions.assertEquals(6000L, account.getMoneyAmount());
        verify(apiService, times(1)).sendDeposit(req, account);
    }

    @Test
    void 출금_성공() {
        // given
        UUID userId = UUID.fromString(tokenInfo.id());
        Account account = new Account();
        account.updateMoney(10000L);
        account.updateTransferLimit(10000L, 1000000L);
        String transAccount = "111222333333";
        AccountMoneyRequest req = new AccountMoneyRequest(
            "transfer",
            "222333444444",
            transAccount,
            5000L,
            1L
        );

        when(accountDao.getAccount(req.account())).thenReturn(account);

        //when
        accountService.updateMoney(userId, req);

        // then
        verify(accountDao, times(1)).getAccount(req.account());
        Assertions.assertEquals(5000L, account.getMoneyAmount());
        verify(apiService, times(1)).sendWithdraw(req, account);
    }

    @Test
    void accountAmountInsufficient() {
        // given
        Account account = new Account();
        account.updateMoney(0L);
        account.updateTransferLimit(10000L, 1000000L);
        String transAccount = "111222333333";
        UUID userId = UUID.randomUUID();
        AccountMoneyRequest req = new AccountMoneyRequest(
            "transfer",
            "222333444444",
            transAccount,
            5000L,
            1L
        );

        when(accountDao.getAccount(req.account())).thenReturn(account);

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.updateMoney(userId, req));
    }

    @Test
    void accountAmountExceedsOneTimeLimit() {
        // given
        Account account = new Account();
        account.updateMoney(1000L);
        account.updateTransferLimit(10000L, 5000L);
        String transAccount = "111222333333";
        UUID userId = UUID.randomUUID();
        AccountMoneyRequest req = new AccountMoneyRequest(
            "transfer",
            "222333444444",
            transAccount,
            5000L,
            1L
        );

        when(accountDao.getAccount(req.account())).thenReturn(account);

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.updateMoney(userId, req));
    }

    @Test
    void 계좌_한도_변경_성공() {
        // given
        Account account = mock(Account.class);
        UUID userId = UUID.randomUUID();
        AccountTransferLimitRequest request = new AccountTransferLimitRequest("account123", 20000L, 5000L);

        when(accountDao.getAccount(request.account())).thenReturn(account);

        // when
        accountService.updateAccountLimit(userId, request);

        // then
        verify(accountDao, times(1)).getAccount(request.account());
        verify(account, times(1))
            .updateTransferLimit(
                request.accountDailyLimit(),
                request.accountOneTimeLimit()
            );
    }

    @Test
    void accountLimitNotValidToken() {
        // given
        AccountTransferLimitRequest request = new AccountTransferLimitRequest("account123", 20000L, 5000L);

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.updateAccountLimit(null, request));
    }

    @Test
    void 계좌_삭제_성공() {
        // given
        UUID userId = UUID.fromString(tokenInfo.id());
        Account account = mock(Account.class);
        when(accountRepository.findUserIdAndAccount(userId, account.getAccount())).thenReturn(Optional.of(account));

        // when
        accountService.deleteAccount(userId, account.getAccount());

        // then
        verify(accountRepository, times(1)).findUserIdAndAccount(userId, account.getAccount());
        verify(account, times(1)).setAccountDisable(false);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void accountIsNotExist() {
        // given
        UUID userId = UUID.randomUUID();
        String account = "111222333333";

        when(accountRepository.findUserIdAndAccount(userId, account)).thenReturn(Optional.empty());

        // when + then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.deleteAccount(userId, account);
        });

        verify(accountRepository).findUserIdAndAccount(userId, account);
    }


}
