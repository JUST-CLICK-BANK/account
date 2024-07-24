package com.click.account.service;

import com.click.account.config.kafka.dto.KafkaStatus;
import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.dto.response.AccountResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.dto.response.AccountUserInfo;
import com.click.account.domain.entity.Account;
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

    @Override
    public void saveAccount(TokenInfo tokenInfo, AccountRequest req) {
        if (tokenInfo == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        UUID userId = UUID.fromString(tokenInfo.id());

        // 중복된 계좌가 있는지 확인 후 새로운 계좌 생성
        String account = makeAccount();

        // 일반 계좌 생성
        if (req.accountStatus().equals("account")) {
            User user = userService.getUser(userId, tokenInfo);
            accountDao.saveAccount(req, account, user);
            return;
        }

        // 모임 통장 계좌 생성
        if (req.accountStatus().equals("group")) {
            User user = userService.getUser(userId, tokenInfo);
            accountDao.saveGroupAccount(req, account, user);
            groupAccountDao.saveGroupToUser(tokenInfo, account, userId);
//            apiService.sendUserCode(user.getUserCode(), account);
            apiService.sendUserCodeAndAccount(user.getUserCode(), account);
       }
    }

    private String makeAccount() {
        String account = GenerateAccount.generateAccount();
        if (accountDao.compareAccount(account)) {
            return makeAccount();
        }
        return account;
    }

    @Override
    @Transactional
    public void updateName(UUID userId, AccountNameRequest req) {
        if (userId == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        Account account = accountDao.getAccount(req.account());
        account.updateName(req.accountName());
    }

    @Override
    public void updatePassword(UUID userId, AccountPasswordRequest req) {
        if (userId == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        Account account = accountDao.getAccount(req.account());
        account.updateName(req.accountPassword());
    }

    @Override
    @Transactional
    public void updateMoney(UUID userId, AccountMoneyRequest req) {
        if (userId == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        Account account = accountDao.getAccount(req.account());

        if (account.getAccountOneTimeLimit() <= req.moneyAmount()) throw new IllegalArgumentException("1회 한도를 초과하였습니다.");

        // 입금 받은 경우
        if (req.accountStatus().equals("deposit")) {
            Long money = account.getMoneyAmount() + req.moneyAmount();
            account.updateMoney(money);
            apiService.sendDeposit(req, account);
        }

        // 출금한 경우
        if (req.accountStatus().equals("transfer")) {
            if (account.getMoneyAmount() <= 0) throw new IllegalArgumentException("잔액이 부족합니다.");
            long money = account.getMoneyAmount()  - req.moneyAmount();
            account.updateMoney(money);
            apiService.sendWithdraw(req, account);
        }
    }

    @Override
    @Transactional
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        if (userId == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        Account account = accountDao.getAccount(req.account());
        account.updateTransferLimit(req.accountDailyLimit(), req.accountOneTimeLimit());
    }

    @Override
    public List<UserAccountResponse> findUserAccountByUserIdAndAccount(UUID userId,TokenInfo tokenInfo) {
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
    public void deleteAccount(UUID userId, String account) {
        Account delete = accountRepository.findUserIdAndAccount(userId, account)
            .orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(false);
        accountRepository.save(delete);
    }

    @Override
    public AccountUserInfo getAccountFromUserId(String requestAccount, TokenInfo tokenInfo) {
        if (tokenInfo == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        Account account = accountDao.getAccount(requestAccount);
        return AccountUserInfo.from(account);
    }
}

