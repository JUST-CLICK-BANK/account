package com.click.account.controller;

import com.click.account.config.utils.jwt.JwtUtils;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.request.group.GroupAccountMemberRequest;
import com.click.account.domain.dto.request.group.GroupAccountMemberStatusRequest;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import com.click.account.service.GroupAccountMemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts/group")
public class GroupAccountMemberController {
    private final GroupAccountMemberService groupAccountMemberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveGroup(
        TokenInfo tokenInfo,
        @RequestBody GroupAccountMemberStatusRequest req
    ) {
        groupAccountMemberService.save(tokenInfo, req.status());
    }

    @PostMapping("wait")
    public void waitMember(
        TokenInfo tokenInfo,
        @RequestParam("account") String account,
        @RequestBody List<GroupAccountMemberRequest> requests
    ) {
        groupAccountMemberService.saveWaitingMember(tokenInfo, account, requests);
    }

    @GetMapping("/accept")
    public List<GroupAccountMemberResponse> acceptMember(
        TokenInfo tokenInfo
    ) {
        return groupAccountMemberService.acceptGroupAccountMember(tokenInfo);
    }

    @DeleteMapping
    public void delete(
        TokenInfo tokenInfo,
        @RequestParam("account") String account
    ) {
        groupAccountMemberService.delete(tokenInfo, account);
    }
}
