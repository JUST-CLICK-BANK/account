package com.click.account.domain.dao;

import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {

    private final AccountRepository accountRepository;

    @Override
    public boolean compareAccount(String generatedAccount) {
        return accountRepository.findByAccount(generatedAccount)
            .isPresent();
    }

    // 추후 saveAccount와 saveGroupAccount 로직 합칠 예정
    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account getAccount(String generatedAccount) {
        return accountRepository.findByAccount(generatedAccount)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void deleteAccount(Account account) {
        account.setAccountDisable(false);
        accountRepository.save(account);
    }
}
