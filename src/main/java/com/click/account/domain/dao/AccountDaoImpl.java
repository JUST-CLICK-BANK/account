package com.click.account.domain.dao;

import com.click.account.config.constants.TransferLimit;
import com.click.account.config.utils.account.GroupCode;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;
import com.click.account.domain.repository.AccountRepository;
import com.click.account.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountDaoImpl implements AccountDao {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public boolean compareAccount(String generatedAccount) {
        return accountRepository.findByAccount(generatedAccount)
            .isPresent();
    }

    @Override
    public void saveAccount(AccountRequest req, String account, User user) {
        accountRepository.save(
            req.toEntity(
                account,
                user.getUserNickName()+"의 통장",
                user,
                TransferLimit.DAILYLIMIT.getTransferLimit(),
                TransferLimit.ONETIMELIMIT.getTransferLimit(),
                true
            )
        );
    }

    @Override
    public void saveGroupAccount(AccountRequest req, String account, User user) {
        accountRepository.save(
            req.toGroupEntity(
                account,
                user.getUserNickName()+"의 모임 통장",
                user,
                TransferLimit.DAILYLIMIT.getTransferLimit(),
                TransferLimit.ONETIMELIMIT.getTransferLimit(),
                GroupCode.getGroupCode(),
                true
            )
        );
    }

    @Override
    public Account getAccount(String generatedAccount) {
        return accountRepository.findByAccount(generatedAccount)
            .orElseThrow(IllegalArgumentException::new);
    }
}
