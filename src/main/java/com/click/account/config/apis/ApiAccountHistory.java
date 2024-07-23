package com.click.account.config.apis;

import com.click.account.domain.dto.request.DepositRequest;
import com.click.account.domain.dto.request.WithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiAccountHistory {
    private FeignAccountHistory feignAccountHistory;

    @Async
    public void sendDepositInfo(DepositRequest req) {
        feignAccountHistory.deposit(req);
    }

    @Async
    public void sendWithdrawInfo(WithdrawRequest req) {
        feignAccountHistory.withdraw(req);
    }

}
