package com.click.account.controller;

import com.click.account.config.utils.jwt.JwtUtils;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.*;
import com.click.account.domain.dto.request.AccountRequest;
import com.click.account.domain.dto.response.AccountResponse;
import com.click.account.domain.entity.Account;
import com.click.account.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    private final JwtUtils jwtUtils;
    private static final UUID HARDCODED_USER_ID = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccount(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.saveAccount(tokenInfo, req);
//        accountService.saveAccount(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }

    @PutMapping("/name")
    public void updateName(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountNameRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateName(UUID.fromString(tokenInfo.id()), req);
//        accountService.updateName(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }

    @PutMapping("/password")
    public void updatePassword(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountPasswordRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updatePassword(UUID.fromString(tokenInfo.id()), req);
//        accountService.updatePassword(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }

    @PutMapping("/amount")
    public void updateMoney(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountMoneyRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateMoney(UUID.fromString(tokenInfo.id()), req);
//        accountService.updateMoney(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }

    @PutMapping("/limit")
    public void updateAccountLimit(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AccountTransferLimitRequest req
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.updateAccountLimit(UUID.fromString(tokenInfo.id()), req);
//        accountService.updateAccountLimit(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), req);
    }
  
    @DeleteMapping()
    public void deleteAccount(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("account") String account
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        accountService.deleteAccount(UUID.fromString(tokenInfo.id()), account);
//        accountService.deleteAccount(UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866"), account);
    }


//@GetMapping()
//public List<Account> getByUserId(@Param UUID userId) {
//    // HARDCODED_USER_ID를 사용하여 실제로는 userId 파라미터를 무시합니다.
//    UUID hardcodedUserId = U/UID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
//    return accountService.findByUserId(hardcodedUserId);
////            .orElseThrow(() -> new IllegalArgumentException("계좌를 찾을 수 없습니다."));
//}

    @GetMapping("disable")
    public List<Account> getDisabledAccountByUserId(@RequestHeader UUID userId) {
        UUID hardcodedUserId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        List<Account> disabledAccounts = accountService.findDisabledAccountByUserId(hardcodedUserId);
        if (disabledAccounts.isEmpty()) {
            throw new IllegalArgumentException("비활성화된 계좌를 찾을 수 없습니다.");
        }
        return disabledAccounts;
    }
    @GetMapping("group")
    public List<String> getGroupAccountCodeByUserIdAndAccount(@RequestHeader UUID userId, @RequestParam("account") String account) {
        UUID hardcodedUserId = UUID.fromString("71a90366-30e6-4e7e-a259-01a7947ff866");
        List<String> groupCodes = accountService.findGroupAccountCodeByUserIdAndAccount(HARDCODED_USER_ID, account);
        if (groupCodes.isEmpty()) {
            throw new IllegalArgumentException(" 그룹 코드를 찾을 수 없습니다.");
        }
        return groupCodes;
    }

}


