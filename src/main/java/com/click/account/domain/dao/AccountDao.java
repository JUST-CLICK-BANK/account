package com.click.account.domain.dao;

import com.click.account.domain.entity.Account;
import java.util.List;
import java.util.UUID;

public interface AccountDao {
    boolean compareAccount(String generatedAccount);
    void saveAccount(Account account);
    Account getAccount(String account);
    List<Account> getAccountFromType(UUID userId, Integer type);
    void deleteAccount(Account account);
}
