package com.click.account.service;

import com.click.account.config.kafka.dto.GroupAccountDTO;
import com.click.account.domain.dto.request.AccountMoneyRequest;
import com.click.account.domain.entity.Account;

public interface ApiService {
    void sendDeposit(AccountMoneyRequest req, Account account);
    void sendWithdraw(AccountMoneyRequest req, Account account);
//    void sendUserCode(String userCode, String account);
    void sendUserCodeAndAccount(String userCode, String account);
}
