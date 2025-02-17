package com.click.account.service.creator;

import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;


public class AccountCreatorImpl implements AccountCreator{

    @Override
    public Account createAccount(AccountRequest req, User user, String makeAccount, Integer type) {
        return req.toEntity(
                makeAccount,
                user.getUserNickName() + "의 통장",
                user,
                true,
                type
        );
    }

    @Override
    public void afterSaveAccount(Account account, AccountRequest req) {

    }
}
