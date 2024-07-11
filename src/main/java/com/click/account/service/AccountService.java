package com.click.account.service;

import com.click.account.domain.dto.request.AccountRequest;

import java.util.UUID;

public interface AccountService {
    void saveAccount(UUID userId, AccountRequest req);
}
