package com.click.account.service;

import com.click.account.domain.dto.request.*;

import java.util.UUID;

public interface AccountService {
    void saveAccount(UUID userId, AccountRequest req);
    void updateName(UUID userId, AccountNameRequest req);
    void updatePassword(UUID userId, AccountPasswordRequest req);
    void updateMoney(UUID userId, AccountMoneyRequest req);
    void updateAccountLimit(UUID userId, AccountTransferLimitRequest req);
    void deleteAccount(UUID userId,String account);
}
