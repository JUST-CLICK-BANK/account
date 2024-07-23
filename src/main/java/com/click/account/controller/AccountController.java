package com.click.account.controller;

import com.click.account.config.utils.jwt.JwtUtils;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.dto.response.AccountUserInfo;
import com.click.account.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*",
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
})
public class AccountController {

    private final AccountService accountService;
    private final JwtUtils jwtUtils;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(
        @RequestHeader("Authorization") String bearerToken
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccount(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody AccountRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.saveAccount(tokenInfo, req);
    }

    @PutMapping("/name")
    public void updateName(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody AccountNameRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateName(UUID.fromString(tokenInfo.id()), req);
    }

    @PutMapping("/password")
    public void updatePassword(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody AccountPasswordRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updatePassword(UUID.fromString(tokenInfo.id()), req);
    }

    @PutMapping("/amount")
    public void updateMoney(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody AccountMoneyRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateMoney(UUID.fromString(tokenInfo.id()), req);
    }

    @PutMapping("/limit")
    public void updateAccountLimit(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody AccountTransferLimitRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateAccountLimit(UUID.fromString(tokenInfo.id()), req);
    }

    @DeleteMapping()
    public void deleteAccount(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam("account") String account
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.deleteAccount(UUID.fromString(tokenInfo.id()), account);
    }
  
    @GetMapping("user-account")
    public List<UserAccountResponse> getAccountByUserId(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        return accountService.findUserAccountByUserIdAndAccount(UUID.fromString(tokenInfo.id()),tokenInfo);
    }



    @GetMapping("/others")
    public AccountUserInfo getAccountUserInfo(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam("account") String account
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        return accountService.getAccountFromUserId(account, tokenInfo);
    }

}


