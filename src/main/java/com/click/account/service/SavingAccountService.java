package com.click.account.service;

import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.dto.request.account.SavingRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.SavingAccount;
import java.util.UUID;

public interface SavingAccountService {
    void save(SavingRequest req, String account);
    void delete(Account reqAccount);
}
