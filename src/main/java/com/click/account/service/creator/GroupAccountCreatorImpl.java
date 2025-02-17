package com.click.account.service.creator;

import com.click.account.domain.dao.GroupAccountDao;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GroupAccountCreatorImpl implements AccountCreator{
    private final GroupAccountDao groupAccountDao;

    @Override
    public Account createAccount(AccountRequest req, User user, String makeAccount, Integer type) {

        return req.toGroupEntity(
                makeAccount,
                user.getUserNickName() + "의 모임 통장",
                user,
                true,
                type
        );
    }

    @Override
    public void afterSaveAccount(Account account, AccountRequest req) {
        groupAccountDao.saveGroupToUser(account.getUser(), account);
    }
}
