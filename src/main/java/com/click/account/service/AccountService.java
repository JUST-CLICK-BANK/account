package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.dto.request.account.AccountNameRequest;
import com.click.account.domain.dto.request.account.AccountPasswordRequest;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.dto.request.account.AccountTransferLimitRequest;
import com.click.account.domain.dto.response.AccountDetailResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.dto.response.AccountUserInfo;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    void saveAccount(TokenInfo tokenInfo, AccountRequest req);
    Boolean checkAccount(String reqAccount);
    List<UserAccountResponse> findUserAccountByUserIdAndAccount(UUID userId,TokenInfo tokenInfo);
    AccountUserInfo getAccountFromUserId(String account, TokenInfo tokenInfo);
    AccountDetailResponse getAccountInfo(TokenInfo tokenInfo, String account);
    void updateName(UUID userId, AccountNameRequest req);
    void updatePassword(UUID userId, AccountPasswordRequest req);
    void updateMoney(UUID userId, AccountMoneyRequest req);
    void updateAccountLimit(UUID userId, AccountTransferLimitRequest req);
    void deleteAccount(UUID userId,String account);
}
