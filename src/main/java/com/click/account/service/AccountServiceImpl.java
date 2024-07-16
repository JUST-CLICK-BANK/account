package com.click.account.service;

import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dto.request.*;
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
    private final AccountRepository accountRepository;

    @Override
    public void saveAccount(UUID userId, AccountRequest req) {
        String account = GenerateAccount.generateAccount();

        // 중복된 계좌가 있는지 확인 필요
        accountDao.compareAccount(account);

        if (req.status().equals("account"))
            accountDao.saveAccount(req, account, userId);
        if (req.status().equals("group"))
            accountDao.saveGroupAccount(req, account, userId);
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
            Long money = accountDao.getAccount(userId, req.account()).getMoneyAmount() + req.moneyAmount();
            accountDao.updateMoney(userId, req.account(), money);
        }
        if (req.status().equals("transfer")) {
            Long money = accountDao.getAccount(userId, req.account()).getMoneyAmount() - req.moneyAmount();
            accountDao.updateMoney(userId, req.account(), money);
        }
    }

    @Override
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        accountDao.updateAccountLimit(userId, req);
    }


    public List<Account> findByUserId(UUID userId) {
        return accountRepository.findByUserId(userId);
    }
    @Override
    public List<Account> findDisabledAccountByUserId(UUID userId) {
        return accountRepository.findByUserIdAndAccountDisable(userId, true);
    }

    @Override
    public List<String> findGroupAccountCodeByUserIdAndAccount(UUID userId, String account) {
        return accountRepository.findByUserIdAndAccountAndAccountDisable(userId, account,true)
                .stream()
                .map(Account::getGroupAccountCode)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId, String account) {
        Account delete = accountRepository.findOptionalByUserIdAndAccount(userId, account).orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(false);
    }
}

