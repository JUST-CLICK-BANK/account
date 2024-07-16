package com.click.account.controller;

import com.click.account.config.utils.jwt.JwtUtils;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.*;
import com.click.account.service.AccountService;
import feign.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    private final JwtUtils jwtUtils;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccount(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.saveAccount(tokenInfo.id(), req);
//        accountService.saveAccount(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }

    @PutMapping("/name")
    public void updateName(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountNameRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateName(tokenInfo.id(), req);
//        accountService.updateName(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }

    @PutMapping("/password")
    public void updatePassword(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountPasswordRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updatePassword(tokenInfo.id(), req);
//        accountService.updatePassword(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }

    @PutMapping("/amount")
    public void updateMoney(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountMoneyRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateMoney(tokenInfo.id(), req);
//        accountService.updateMoney(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }

    @PutMapping("/limit")
    public void updateAccountLimit(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountTransferLimitRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateAccountLimit(tokenInfo.id(), req);
//        accountService.updateAccountLimit(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }
  
    @DeleteMapping()
    public void deleteAccount(
            @RequestHeader("Authorization") String bearerToken,
            @Param("account") String account
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.deleteAccount(tokenInfo.id(), account);
//        accountService.deleteAccount(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), account);
    }
}