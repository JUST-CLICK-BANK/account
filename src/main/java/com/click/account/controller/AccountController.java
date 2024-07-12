package com.click.account.controller;

import com.click.account.domain.entity.Account;
import com.click.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;


}
