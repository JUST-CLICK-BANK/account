package com.click.account.service.creator;

import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;

public interface AccountCreator {
    Account createAccount(AccountRequest req, User user, String makeAccount, Integer type);
    void afterSaveAccount(Account account, AccountRequest req);
}
