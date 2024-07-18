package com.click.account.service;

import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.dto.response.AccountResponse;
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

    @Override
    public void saveAccount(TokenInfo tokenInfo, AccountRequest req) {
        UUID userId = UUID.fromString(tokenInfo.id());

        // 중복된 계좌가 있는지 확인 후 새로운 계좌 생성
        String account = makeAccount();
        // 일반 계좌 생성
        if (req.status().equals("account")) {
            accountDao.saveAccount(req, account, userId, tokenInfo);
        }

        // 모임 통장 계좌 생성
        if (req.status().equals("group")) {
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
        accountDao.updateName(userId, req);
    }

    @Override
    public void updatePassword(UUID userId, AccountPasswordRequest req) {
        accountDao.updatePassword(userId, req);
    }

    @Override
    public void updateMoney(UUID userId, AccountMoneyRequest req) {
        if (req.status().equals("deposit")) {
            Long money =
                accountDao.getAccount(req.account()).getMoneyAmount() + req.moneyAmount();
            accountDao.updateMoney(userId, req.account(), money);
        }
        // 돈이 0원일때 예외 처리 필요
        if (req.status().equals("transfer")) {
            Long money =
                accountDao.getAccount(req.account()).getMoneyAmount() - req.moneyAmount();
            accountDao.updateMoney(userId, req.account(), money);
        }
    }

    @Override
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        accountDao.updateAccountLimit(userId, req);
    }


    public List<Account> findByUserId(UUID userId) {
        return accountRepository.findByUser_UserId(userId);
    }

    @Override
    public List<AccountResponse> findDisabledAccountByUserId(UUID userId) {
        return accountRepository.findByUser_UserIdAndAccountDisable(userId, true)
            .stream()
            .map(AccountResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public String findGroupAccountCodeByUserIdAndAccount(UUID userId, String account) {
        return accountRepository.findByUser_UserIdAndAccountAndAccountDisable(userId, account, true)
            .orElseThrow(IllegalArgumentException::new)
            .getGroupAccountCode();
    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId, String account) {
        Account delete = accountRepository.findOptionalByUser_UserIdAndAccount(userId, account)
            .orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(false);
    }

    @Override
    public AccountUserInfo getAccountFromUserId(String requestAccount) {
        Account account = accountDao.getAccount(requestAccount);
        return AccountUserInfo.from(account);
    }
}

