package com.click.account.service;

import com.click.account.config.apis.FeignAccountHistory;
import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.dto.response.AccountResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.dto.response.AccountUserInfo;
import com.click.account.domain.entity.Account;
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
    private final GenerateAccount generateAccount;
    private final FeignAccountHistory feignDeposit;

    @Override
    public void saveAccount(TokenInfo tokenInfo, AccountRequest req) {
        if (tokenInfo == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        UUID userId = UUID.fromString(tokenInfo.id());

        // 중복된 계좌가 있는지 확인 후 새로운 계좌 생성
        String account = makeAccount();
        // 일반 계좌 생성
        if (req.accountStatus().equals("account")) {
            accountDao.saveAccount(req, account, userId, tokenInfo);
        }

        // 모임 통장 계좌 생성
        if (req.accountStatus().equals("group")) {
            accountDao.saveGroupAccount(req, account, userId, tokenInfo);
            groupAccountDao.saveGroupToUser(tokenInfo, account, userId);
        }
    }

    private String makeAccount() {
        String account = generateAccount.generateAccount();
        if (accountDao.compareAccount(account)) {
            return makeAccount();
        }
        return account;
    }

    @Override
    public void updateName(UUID userId, AccountNameRequest req) {
        if (userId == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        accountDao.updateName(userId, req);
    }

    @Override
    public void updatePassword(UUID userId, AccountPasswordRequest req) {
        if (userId == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        accountDao.updatePassword(userId, req);
    }

    @Override
    public void updateMoney(UUID userId, AccountMoneyRequest req) {
        if (userId == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        Account accountInfo = accountDao.getAccount(req.account());

        // 입금 받은 경우
        if (req.accountStatus().equals("deposit")) {
            Long money = accountInfo.getMoneyAmount() + req.moneyAmount();
            accountDao.updateMoney(userId, req.account(), money);
            DepositRequest depositRequest = DepositRequest.toTranfer(req, accountInfo.getAccountName(), accountInfo.getMoneyAmount());
            feignDeposit.deposit(depositRequest);
        }

        // 출금한 경우
        if (req.accountStatus().equals("transfer")) {
            if (accountInfo.getMoneyAmount() <= 0) throw new IllegalArgumentException("잔액이 부족합니다.");
            long money = accountInfo.getMoneyAmount()  - req.moneyAmount();
            accountDao.updateMoney(userId, req.account(), money);
            WithdrawRequest withdrawRequest = WithdrawRequest.toTransfer(req, accountInfo.getAccountName(), accountInfo.getMoneyAmount());
            feignDeposit.withdraw(withdrawRequest);
        }
    }

    @Override
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        if (userId == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        accountDao.updateAccountLimit(userId, req);
    }

    @Override
    public List<UserAccountResponse> findUserAccountByUserIdAndAccount(UUID userId,TokenInfo tokenInfo) {
        List<Account> disabledAccount = accountRepository.findByUser_UserIdAndAccountDisable(userId,true);
        if (disabledAccount.isEmpty()) {
            throw new IllegalArgumentException("게좌 없음");
        }
        List<AccountResponse> accountResponses = disabledAccount.stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());

        return List.of(UserAccountResponse.from(accountResponses, tokenInfo));
    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId, String account) {
        Account delete = accountRepository.findOptionalByUser_UserIdAndAccount(userId, account)
            .orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(false);
    }

    @Override
    public AccountUserInfo getAccountFromUserId(String requestAccount, TokenInfo tokenInfo) {
        if (tokenInfo == null) throw new IllegalArgumentException("유효하지 않는 토큰입니다.");
        Account account = accountDao.getAccount(requestAccount);
        return AccountUserInfo.from(account);
    }
}

