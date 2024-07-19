package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.dto.response.GroupAccountResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.dto.response.AccountResponse;
import com.click.account.domain.dto.response.AccountUserInfo;
import com.click.account.domain.entity.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    void saveAccount(TokenInfo tokenInfo, AccountRequest req);
    void updateName(UUID userId, AccountNameRequest req);
    void updatePassword(UUID userId, AccountPasswordRequest req);
    void updateMoney(UUID userId, AccountMoneyRequest req);
    void updateAccountLimit(UUID userId, AccountTransferLimitRequest req);
    void deleteAccount(UUID userId,String account);

//    List<Account> findByUserId(UUID userId);
//    List<GroupAccountResponse> findDisabledAccountByUserId(UUID userId);
    String findGroupAccountCodeByUserIdAndAccount(UUID userId, String account);
    List<UserAccountResponse> findUserAccountByUserIdAndAccount(UUID userId,TokenInfo tokenInfo);
    AccountUserInfo getAccountFromUserId(String account);
    String findGroupAccountCodeByUserIdAndAccount(UUID userId, String account);
}
