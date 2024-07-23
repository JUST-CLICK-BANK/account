package com.click.account.service;

import com.click.account.config.apis.FeignAccountHistory;
import com.click.account.domain.dto.request.AccountMoneyRequest;
import com.click.account.domain.dto.request.DepositRequest;
import com.click.account.domain.dto.request.WithdrawRequest;
import com.click.account.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final FeignAccountHistory feignAccountHistory;

    @Override
    public void sendDeposit(AccountMoneyRequest req, Account account) {
        log.info(req.moneyAmount().toString());
        DepositRequest depositRequest = DepositRequest.toTranfer(req, account.getAccountName(), account.getMoneyAmount());
        feignAccountHistory.deposit(depositRequest);
    }

    @Override
    public void sendWithdraw(AccountMoneyRequest req, Account account) {
        log.info(req.moneyAmount().toString());
        WithdrawRequest withdrawRequest = WithdrawRequest.toTransfer(req, account.getAccountName(), account.getMoneyAmount());
        feignAccountHistory.withdraw(withdrawRequest);
    }
}
