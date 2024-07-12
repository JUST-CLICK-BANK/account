package com.click.account.service;

import com.click.account.config.constants.TransferLimit;
import com.click.account.config.utils.GenerateAccount;
import com.click.account.config.utils.GroupCode;
import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Transactional
    public void deleteAccount(UUID userId, String account) {
        Account delete = accountRepository.findByUserIdAndAccount(userId, account).orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(false);
    }
}

