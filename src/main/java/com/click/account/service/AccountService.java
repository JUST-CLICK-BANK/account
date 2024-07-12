package com.click.account.service;

import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.dto.request.GroupAccountRequest;

import java.util.UUID;

public interface AccountService {
    void saveAccount(UUID userId, AccountRequest req);
    void saveGroupAccount(UUID userId, GroupAccountRequest req);

    void deleteAccount(UUID userId,String account);
    void deleteGroupAccount(UUID userId,String account);

}
