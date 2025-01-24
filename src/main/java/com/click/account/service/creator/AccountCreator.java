package com.click.account.service.creator;

import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.User;

public interface AccountCreator {
    String createAccount(AccountRequest req, User user, String makeAccount, Integer type);
}
