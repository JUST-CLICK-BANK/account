package com.click.account.domain.dao;

import com.click.account.config.constants.TransferLimit;
import com.click.account.config.utils.GroupCode;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {
    private final AccountRepository accountRepository;

    @Override
    public void compareAccount(String generatedAccount) {
        accountRepository.findByAccount(generatedAccount)
                .filter(byAccount -> byAccount.getAccount().equals(generatedAccount))
                .ifPresent(byAccount -> {
                    throw new IllegalArgumentException("이미 있는 계좌입니다.");
                });
    }

    @Override
    public void saveAccount(AccountRequest req, String account, UUID userId) {
        accountRepository.save(
                req.toEntity(
                        account,
                        userId,
                        TransferLimit.getDailyLimit(),
                        TransferLimit.getOnetimeLimit(),
                        true
                )
        );
    }

    @Override
    public void saveGroupAccount(AccountRequest req, String account, UUID userId) {
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

}
