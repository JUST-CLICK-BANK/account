package com.click.account.controller;

import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping()
    public void saveAccount(@RequestBody AccountRequest req) {
        accountService.saveAccount(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
}

}