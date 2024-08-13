package com.click.account.controller;

import com.click.account.config.utils.jwt.JwtUtils;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.account.AccountMoneyRequest;
import com.click.account.domain.dto.request.account.AccountNameRequest;
import com.click.account.domain.dto.request.account.AccountPasswordRequest;
import com.click.account.domain.dto.request.account.AccountRequest;
import com.click.account.domain.dto.request.account.AccountTransferLimitRequest;
import com.click.account.domain.dto.response.AccountAmountResponse;
import com.click.account.domain.dto.response.AccountDetailResponse;
import com.click.account.domain.dto.response.AccountInfoResponse;
import com.click.account.domain.dto.response.AutoTransferAccountResponse;
import com.click.account.domain.dto.response.UserAccountResponse;
import com.click.account.domain.dto.response.AccountUserInfo;
import com.click.account.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAccount(
        TokenInfo tokenInfo,
        @RequestBody AccountRequest req
    ) {
        accountService.saveAccount(tokenInfo, req);
    }

    @GetMapping("user-account")
    public List<UserAccountResponse> getAccountByUserId(
        TokenInfo tokenInfo
    ) {
        return accountService.findUserAccountByUserIdAndAccount(tokenInfo);
    }

    // 상대 계좌 정보 가져오기
    @GetMapping("/others")
    public AccountUserInfo getAccountUserInfo(
        TokenInfo tokenInfo,
        @RequestParam("account") String account
    ) {
        return accountService.getAccountFromUserId(account, tokenInfo);
    }

    // pay에 보낼 계좌에 대한 잔액 확인 로직
    @GetMapping("/pay")
    public AccountAmountResponse getAccountAmount(@RequestParam("account") String account) {
        return accountService.getAccountMount(account);
    }

    @GetMapping("/card")
    public AccountInfoResponse getAccountInfo(@RequestParam("account") String account) {
        return accountService.getAccountInfoToCard(account);
    }

    // 모임 통장 멤버 목록
    @GetMapping("/group")
    public AccountDetailResponse getAccount(
        TokenInfo tokenInfo,
        @RequestParam("account") String account
    ) {
        return accountService.getAccountInfo(tokenInfo, account);
    }

    @GetMapping("/saving")
    public List<AutoTransferAccountResponse> getAccounts(
        TokenInfo tokenInfo
    ) {
        return accountService.getAccounts(tokenInfo);
    }

    @PutMapping("/name")
    public void updateName(
        TokenInfo tokenInfo,
        @RequestBody AccountNameRequest req
    ) {
        accountService.updateName(UUID.fromString(tokenInfo.id()), req);
    }

    @PutMapping("/password")
    public void updatePassword(
        TokenInfo tokenInfo,
        @RequestBody AccountPasswordRequest req
    ) {
        accountService.updatePassword(UUID.fromString(tokenInfo.id()), req);
    }

    @PutMapping("/amount")
    public void updateMoney(
        TokenInfo tokenInfo,
        @RequestBody AccountMoneyRequest req
    ) {
        System.out.println(req.toString());
        accountService.updateMoney(UUID.fromString(tokenInfo.id()), req);
    }

    @PutMapping("/limit")
    public void updateAccountLimit(
        TokenInfo tokenInfo,
        @RequestBody AccountTransferLimitRequest req
    ) {
        accountService.updateAccountLimit(UUID.fromString(tokenInfo.id()), req);
    }

    @DeleteMapping()
    public void deleteAccount(
        TokenInfo tokenInfo,
        @RequestParam("account") String account
    ) {
        accountService.deleteAccount(tokenInfo, account);
    }

}


