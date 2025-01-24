package com.click.account.service.creator;

import com.click.account.domain.dao.AccountDao;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;
import com.click.account.service.SavingAccountService;
import com.click.account.service.TransferService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavingAccountCreatorImpl implements AccountCreator{
    private final AccountDao accountDao;
    private final SavingAccountService savingAccountService;
    private final TransferService transferService;

    @Override
    public String createAccount(AccountRequest req, User user, String makeAccount, Integer type) {
        Account account = req.toSavingEntity(
                makeAccount,
                user.getUserNickName() + "적금 통장",
                user,
                true,
                type
        );
        accountDao.saveAccount(account);
        savingAccountService.save(req.savingRequest(), makeAccount);
        transferService.save(req.transferRequest(), makeAccount);
        return "";
    }
}
