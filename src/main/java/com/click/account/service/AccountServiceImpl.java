package com.click.account.service;

import com.click.account.config.constants.TransferLimit;
import com.click.account.config.utils.GenerateAccount;
import com.click.account.config.utils.GroupCode;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;


    @Override
    public void saveAccount(UUID userId, AccountRequest req) {
        String account = GenerateAccount.generateAccount();

        // 중복된 계좌가 있는지 확인 필요
        Account byAccount = accountRepository.findByAccount(account).orElseThrow(IllegalArgumentException::new);
        if (byAccount.getAccount().equals(account)) throw new IllegalArgumentException();

        if (req.status().equals("account"))
            accountRepository.save(req.toEntity(GenerateAccount.generateAccount(), userId, TransferLimit.getDailyLimit(), TransferLimit.getOnetimeLimit(), true));
        if (req.status().equals("group"))
            accountRepository.save(
                    req.toGroupEntity(
                        account,
                        userId,
                        TransferLimit.getDailyLimit(),
                        TransferLimit.getOnetimeLimit(),
                        GroupCode.getGroupCode(),
                        true
                    )
            );
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

