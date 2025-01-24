package com.click.account.service.creator;

import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GroupAccountCreatorImpl implements AccountCreator{
    private final AccountDao accountDao;
    private final GroupAccountDao groupAccountDao;

    @Override
    public String createAccount(AccountRequest req, User user, String makeAccount, Integer type) {
        Account account = req.toGroupEntity(
                makeAccount,
                user.getUserNickName() + "의 모임 통장",
                user,
                true,
                type
        );
        accountDao.saveAccount(account);
        groupAccountDao.saveGroupToUser(user, account);
        return "";
    }
}
