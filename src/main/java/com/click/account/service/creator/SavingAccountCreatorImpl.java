package com.click.account.service.creator;

import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;
import com.click.account.service.SavingAccountService;
import com.click.account.service.TransferService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavingAccountCreatorImpl implements AccountCreator{
    private final SavingAccountService savingAccountService;
    private final TransferService transferService;

    @Override
    public Account createAccount(AccountRequest req, User user, String makeAccount, Integer type) {

        return req.toSavingEntity(
                makeAccount,
                user.getUserNickName() + "적금 통장",
                user,
                true,
                type
        );
    }

    @Override
    public void afterSaveAccount(Account account, AccountRequest req) {
        savingAccountService.save(req.savingRequest(), account.getAccount());
        transferService.save(req.transferRequest(), account.getAccount());
    }
}
