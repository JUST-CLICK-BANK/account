package com.click.account.service;

import com.click.account.domain.dto.request.account.SavingRequest;
import com.click.account.domain.entity.SavingAccount;

public interface SavingAccountService {
    void save(SavingRequest req, String account);
}
