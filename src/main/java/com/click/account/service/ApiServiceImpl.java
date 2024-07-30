package com.click.account.service;

import com.click.account.config.apis.ApiAccountHistory;
import com.click.account.config.apis.ApiFriendship;
import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.dto.request.account.DepositRequest;
import com.click.account.domain.dto.request.account.WithdrawRequest;
import com.click.account.domain.dto.response.FriendResponse;
import com.click.account.domain.entity.Account;
import com.click.account.domain.entity.Friend;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private final ApiAccountHistory apiAccountHistory;
    private final ApiFriendship apiFriendship;

    @Override
    public void sendDeposit(AccountMoneyRequest req, Account account) {
        log.info(req.moneyAmount().toString());
        DepositRequest depositRequest = DepositRequest.toTranfer(req, account.getAccountName(), account.getMoneyAmount());
        apiAccountHistory.sendDepositInfo(depositRequest);
    }

    @Override
    public void sendWithdraw(AccountMoneyRequest req, Account account) {
        log.info(req.moneyAmount().toString());
        WithdrawRequest withdrawRequest = WithdrawRequest.toTransfer(req, account.getAccountName(), account.getMoneyAmount());
        apiAccountHistory.sendWithdrawInfo(withdrawRequest);
    }

    @Override
    public List<Friend> getFriendsInfo(String userCode, String account) {
        log.info(userCode);
        List<Friend> friends = new ArrayList<>();

        List<FriendResponse> friendResponses = apiFriendship.inviteFriend(userCode);
        friendResponses.forEach(friendResponse -> {
            Friend friend = Friend.builder()
                .userId(friendResponse.id())
                .userImg(friendResponse.img())
                .userName(friendResponse.name())
                .userCode(friendResponse.code())
                .account(account)
                .build();
            friends.add(friend);
        });
        log.info(friends.toString());

        return friends;
    }
}
