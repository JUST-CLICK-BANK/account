package com.click.account.service.creator;

import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountCreatorImpl implements AccountCreator{
    private final AccountDao accountDao;

    @Override
    public String createAccount(AccountRequest req, User user, String makeAccount, Integer type) {
        Account account = req.toEntity(
                makeAccount,
                user.getUserNickName() + "의 통장",
                user,
                true,
                type
        );
        accountDao.saveAccount(account);
        return account.getAccount();
    }
}
