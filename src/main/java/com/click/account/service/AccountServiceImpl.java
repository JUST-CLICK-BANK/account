package com.click.account.service;

import com.click.account.config.constants.AccountType;
import com.click.account.config.exception.InsufficientAmountException;
import com.click.account.config.exception.LimitTransferException;
import com.click.account.config.exception.NotExistAccountException;
import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.account.GroupCode;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.dto.request.account.AccountNameRequest;
import com.click.account.domain.dto.request.account.AccountPasswordRequest;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.dto.request.account.AccountTransferLimitRequest;
import com.click.account.domain.dto.response.AccountDetailResponse;
import com.click.account.domain.dto.response.AccountResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.dto.response.AccountUserInfo;
import com.click.account.domain.dto.response.UserResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.entity.User;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final GroupAccountDao groupAccountDao;
    private final AccountRepository accountRepository;
    private final ApiService apiService;
    private final UserService userService;
    private final FriendService friendService;
    private final TransferService transferService;

    @Override
    public void saveAccount(TokenInfo tokenInfo, AccountRequest req) {
        User user = userService.getUser(tokenInfo);
        Integer type = AccountType.fromString(req.accountStatus());

        // 중복된 계좌가 있는지 확인 후 새로운 계좌 생성
        String makeAccount = makeAccount();

        // 일반 계좌 생성
        if (type == 1) {
            Account account = req.toEntity(
                makeAccount,
                user.getUserNickName()+"의 통장",
                user,
                true,
                type
            );
            accountDao.saveAccount(account);
        }
        // 모임 통장 계좌 생성
        else if (type == 2) {
            friendService.save(user.getUserCode(), makeAccount);
            Account account = req.toGroupEntity(
                makeAccount,
                user.getUserNickName()+"의 모임 통장",
                user,
                GroupCode.getGroupCode(),
                true,
                type
            );
            accountDao.saveAccount(account);
            groupAccountDao.saveGroupToUser(user, account);
       }
        // 적금 계좌 생성
        else if (type == 3) {
            Account account = req.toSavingEntity(
                makeAccount,
                user.getUserNickName()+"적금 통장",
                user,
                true,
                type
            );
            transferService.save(account, req.savingAccountReqeust());
            accountDao.saveAccount(account);
        }
    }

    private String makeAccount() {
        String account = GenerateAccount.generateAccount();
        if (accountDao.compareAccount(account)) {
            return makeAccount();
        }
        return account;
    }

    // 계좌 유효성 검증
    public Boolean checkAccount(String reqAccount) {
        Account account = accountDao.getAccount(reqAccount);
        if (account == null) return false;
        return true;
    }

    @Override
    public List<UserAccountResponse> findUserAccountByUserIdAndAccount(UUID userId, TokenInfo tokenInfo) {
        List<Account> disabledAccount = accountRepository.findAccounts(userId,true);
        if (disabledAccount.isEmpty()) {
            return List.of();
        }
        List<AccountResponse> accountResponses = disabledAccount.stream()
            .map(AccountResponse::from)
            .collect(Collectors.toList());

        return List.of(UserAccountResponse.from(accountResponses, tokenInfo));
    }

    @Override
    public AccountUserInfo getAccountFromUserId(String requestAccount, TokenInfo tokenInfo) {
        Account account = accountDao.getAccount(requestAccount);
        return AccountUserInfo.from(account, account.getUser().getUserId().toString());
    }

    @Override
    public AccountDetailResponse getAccountInfo(TokenInfo tokenInfo, String reqAccount) {
        Account account = accountDao.getAccount(reqAccount);

        List<UserResponse> userResponses = account.getGroupAccountMembers().stream()
            .map(GroupAccountMember::getUser)
            .distinct()
            .map(user -> UserResponse.from(user.getUserNickName(), user.getUserPorfileImg(), user.getUserCode()))
            .toList();

        return AccountDetailResponse.from(account, userResponses);
    }

    @Override
    @Transactional
    public void updateName(UUID userId, AccountNameRequest req) {
        Account account = accountDao.getAccount(req.account());
        account.updateName(req.accountName());
    }

    @Override
    @Transactional
    public void updatePassword(UUID userId, AccountPasswordRequest req) {
        Account account = accountDao.getAccount(req.account());
        account.updatePassword(req.accountPassword());
    }

    @Override
    @Transactional
    public void updateMoney(UUID userId, AccountMoneyRequest req) {
        Account account = accountDao.getAccount(req.account());

        if (account.getAccountOneTimeLimit() <= req.moneyAmount()) throw new LimitTransferException(
            account.getAccountOneTimeLimit());

        // 입금 받은 경우
        if (req.accountStatus().equals("deposit")) {
            Long money = account.getMoneyAmount() + req.moneyAmount();
            account.updateMoney(money);
            apiService.sendDeposit(req, account);
        }

        // 출금한 경우
        if (req.accountStatus().equals("transfer")) {
            if (account.getMoneyAmount() <= 0) throw new InsufficientAmountException(req.moneyAmount());
            // 일화 한도를 넘었을 경우 에러
            if (req.moneyAmount() >= account.getAccountOneTimeLimit()) throw new LimitTransferException(
                account.getAccountOneTimeLimit());
            long money = account.getMoneyAmount()  - req.moneyAmount();
            account.updateMoney(money);
            apiService.sendWithdraw(req, account);
        }
    }

    @Override
    @Transactional
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        Account account = accountDao.getAccount(req.account());
        account.updateTransferLimit(req.accountDailyLimit(), req.accountOneTimeLimit());
    }

    @Override
    public void deleteAccount(UUID userId, String reqAccount) {
        Account account = accountRepository.findUserIdAndAccount(userId, reqAccount)
            .orElseThrow(NotExistAccountException::new);
        account.setAccountDisable(false);
        accountRepository.save(account);
    }
}

