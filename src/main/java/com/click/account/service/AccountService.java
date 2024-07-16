package com.click.account.service;

import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.entity.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {
    void saveAccount(UUID userId, AccountRequest req);
    void deleteAccount(UUID userId,String account);
    List<Account> findByUserId(UUID userId);
    List<Account> findDisabledAccountByUserId(UUID userId);
    List<String> findGroupAccountCodeByUserIdAndAccount(UUID userId, String account);
}
