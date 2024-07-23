package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.entity.Account;

import com.click.account.domain.entity.User;
import java.util.UUID;

public interface AccountDao {
    boolean compareAccount(String generatedAccount);
    void saveAccount(AccountRequest req, String account, User user);
    void saveGroupAccount(AccountRequest req, String account, User user);
    Account getAccount(String Account);
}
