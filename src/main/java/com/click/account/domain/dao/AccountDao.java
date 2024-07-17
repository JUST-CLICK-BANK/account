package com.click.account.domain.dao;

import com.click.account.domain.dto.request.*;
import com.click.account.domain.entity.Account;

import java.util.UUID;

public interface AccountDao {
    boolean compareAccount(String generatedAccount);
    void saveAccount(AccountRequest req, String account, UUID userId);
    void saveGroupAccount(AccountRequest req, String account, UUID userId);
    Account getAccount(UUID userId, String Account);
    void updateName(UUID userId, AccountNameRequest req);
    void updatePassword(UUID userId, AccountPasswordRequest req);
    void updateMoney(UUID userId, String generAccount, Long moneyAmount);
    void updateAccountLimit(UUID userId, AccountTransferLimitRequest req);
}
