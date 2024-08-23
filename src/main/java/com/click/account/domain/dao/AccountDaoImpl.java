package com.click.account.domain.dao;

import com.click.account.domain.entity.Account;
import com.click.account.domain.repository.AccountRepository;
import java.util.List;
import java.util.UUID;
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
    public Account getAccount(String account) {
        return accountRepository.findByAccount(account)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Account> getAccountFromType(UUID userId, Integer type) {
        return accountRepository.findAccountToType(userId, type, true);
    }

    @Override
    public void deleteAccount(Account account) {
        account.setAccountAble(false);
        accountRepository.save(account);
    }
}
