package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.entity.Account;

import com.click.account.domain.entity.User;
import java.util.UUID;

public interface AccountDao {
    boolean compareAccount(String generatedAccount);
    void saveAccount(AccountRequest req, String account, UUID userId, TokenInfo tokenInfo);
    void saveGroupAccount(AccountRequest req, String account, UUID userId, TokenInfo tokenInfo);
    User getUser(UUID userId, TokenInfo tokenInfo);
    Account getAccount(String Account);
    void updateName(UUID userId, AccountNameRequest req);
    void updatePassword(UUID userId, AccountPasswordRequest req);
    void updateMoney(UUID userId, String generAccount, Long moneyAmount);
    void updateAccountLimit(UUID userId, AccountTransferLimitRequest req);
}
