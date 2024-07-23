package com.click.account.config.apis;

import com.click.account.domain.dto.request.DepositRequest;
import com.click.account.domain.dto.request.WithdrawRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-history", url = "http://api/v1/histories")
public interface FeignAccountHistory {

    @PostMapping("/deposit")
    void deposit(@RequestBody DepositRequest req);

    @PostMapping("/withdraw")
    void withdraw(@RequestBody WithdrawRequest req);
}
