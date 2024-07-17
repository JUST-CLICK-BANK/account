package com.click.account.domain.dao;

import com.click.account.config.constants.TransferLimit;
import com.click.account.config.utils.account.GenerateAccount;
import com.click.account.config.utils.account.GroupCode;
import com.click.account.domain.dto.request.*;
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
    public boolean compareAccount(String generatedAccount) {
        return accountRepository.findByAccount(generatedAccount)
                .filter(byAccount -> byAccount.getAccount().equals(generatedAccount))
                .isPresent();
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

    @Override
    public Account getAccount(UUID userId, String generatedAccount) {
        return accountRepository.findOptionalByUserIdAndAccount(userId, generatedAccount).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void updateName(UUID userId, AccountNameRequest req) {
        Account account = getAccount(userId, req.account());
        account.updateName(req.accountName());
        accountRepository.save(account);
    }

    @Override
    public void updatePassword(UUID userId, AccountPasswordRequest req) {
        Account account = getAccount(userId, req.account());
        account.updatePassword(req.accountPassword());
    }

    @Override
    public void updateMoney(UUID userId, String generatedAccount, Long moneyAmount) {
        Account account = getAccount(userId, generatedAccount);
        account.updateMoney(moneyAmount);
    }

    @Override
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        Account account = getAccount(userId, req.account());
        account.updateTransferLimit(req.accountDailyLimit(), req.accountOneTimeLimit());
    }

}
