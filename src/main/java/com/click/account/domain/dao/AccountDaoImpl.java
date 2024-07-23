package com.click.account.domain.dao;

import com.click.account.config.constants.TransferLimit;
import com.click.account.config.utils.account.GroupCode;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.User;
import com.click.account.domain.repository.AccountRepository;
import com.click.account.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

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
    public void saveAccount(AccountRequest req, String account, UUID userId, TokenInfo tokenInfo) {
        User user = getUser(userId, tokenInfo);

        accountRepository.save(
            req.toEntity(
                account,
                tokenInfo.name()+"의 통장",
                user,
                TransferLimit.getDailyLimit(),
                TransferLimit.getOnetimeLimit(),
                true
            )
        );
    }

    @Override
    public void saveGroupAccount(AccountRequest req, String account, UUID userId, TokenInfo tokenInfo) {
        User user = getUser(userId, tokenInfo);

        accountRepository.save(
            req.toGroupEntity(
                account,
                tokenInfo.name()+"의 모임 통장",
                user,
                TransferLimit.getDailyLimit(),
                TransferLimit.getOnetimeLimit(),
                GroupCode.getGroupCode(),
                true
            )
        );
    }

    @Override
    public User getUser(UUID userId, TokenInfo tokenInfo) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder()
                .userId(userId)
                .userNickName(tokenInfo.name())
                .userPorfileImg(tokenInfo.img())
                .userCode(tokenInfo.code())
                .rank(tokenInfo.rank())
                .build();
            userRepository.save(user);
        }

        return user;
    }

    @Override
    public Account getAccount(String generatedAccount) {
        return accountRepository.findByAccount(generatedAccount)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void updateName(UUID userId, AccountNameRequest req) {
        Account account = getAccount(req.account());
        account.updateName(req.accountName());
        accountRepository.save(account);
    }

    @Override
    public void updatePassword(UUID userId, AccountPasswordRequest req) {
        Account account = getAccount(req.account());
        account.updatePassword(req.accountPassword());
        accountRepository.save(account);
    }

    @Override
    public void updateMoney(UUID userId, String generatedAccount, Long moneyAmount) {
        Account account = getAccount(generatedAccount);
        account.updateMoney(moneyAmount);
        accountRepository.save(account);
    }

    @Override
    public void updateAccountLimit(UUID userId, AccountTransferLimitRequest req) {
        Account account = getAccount(req.account());
        account.updateTransferLimit(req.accountDailyLimit(), req.accountOneTimeLimit());
        accountRepository.save(account);
    }

}
