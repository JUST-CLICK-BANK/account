package com.click.account.domain.dao;

import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;

import java.util.UUID;

public interface AccountDao {
    void compareAccount(String generatedAccount);
    void saveAccount(AccountRequest req, String account, UUID userId);
    void saveGroupAccount(AccountRequest req, String account, UUID userId);
}
