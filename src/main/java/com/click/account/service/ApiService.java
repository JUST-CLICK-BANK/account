package com.click.account.service;

import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Friend;
import java.util.List;

public interface ApiService {
    void sendDeposit(AccountMoneyRequest req, Account account);
    void sendWithdraw(AccountMoneyRequest req, Account account);
    List<Friend> getFriendsInfo(String userCode, String account);
}
