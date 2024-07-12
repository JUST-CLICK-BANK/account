package com.click.account.service;

import com.click.account.config.constants.TransferLimit;
import com.click.account.config.utils.GenerateAccount;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;


    @Override
    public void saveAccount(UUID userId, AccountRequest req) {
        if (req == null) throw new IllegalArgumentException();
        // 중복된 계좌가 있는지 확인 필요
        accountRepository.save(req.toEntity(GenerateAccount.generateAccount(), userId, TransferLimit.getDailyLimit(), TransferLimit.getOnetimeLimit(), true));
    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId, String account) {
        Account delete = accountRepository.findByUserIdAndAccount(userId, account).orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(false);
    }


    @Override
    @Transactional
    public void deleteGroupAccount(UUID userId, String account) {
        Account delete = accountRepository.findByUserIdAndAccount(userId, account).orElseThrow(IllegalArgumentException::new);
        delete.setAccountDisable(true);

    }
}

