package com.click.account.service;

import com.click.account.domain.dto.request.account.SavingAccountReqeust;
import com.click.account.domain.entity.Account;

public interface TransferService {
    void save(Account account, SavingAccountReqeust req);

}
