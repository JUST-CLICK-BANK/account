package com.click.account.domain.dao;

import com.click.account.domain.entity.Account;

public interface AccountDao {
    boolean compareAccount(String generatedAccount);
    void saveAccount(Account account);
    Account getAccount(String Account);
    void deleteAccount(Account account);
}
