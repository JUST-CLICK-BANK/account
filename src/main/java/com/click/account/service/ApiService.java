package com.click.account.service;

import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Friend;
import java.util.List;

public interface ApiService {
    void sendDeposit(AccountMoneyRequest req, Account account, String nickname);
    void sendWithdraw(AccountMoneyRequest req, Account account, String nickname);
    List<Friend> getFriendsInfo(String userCode, String account);
    void sendAccount(String token, Account account);
}
