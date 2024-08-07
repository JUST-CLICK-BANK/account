package com.click.account.controller;

import com.click.account.config.utils.jwt.JwtUtils;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.response.FriendResponse;
import com.click.account.service.FriendService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/accounts/friends")
public class FriendController {
    private final FriendService friendService;
    private final JwtUtils jwtUtils;

    @GetMapping()
    public List<FriendResponse> getFriends(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam("account") String account
    ) {
        String token = bearerToken.substring(7);
        TokenInfo tokenInfo = jwtUtils.parseUserToken(token);
        return friendService.getFriends(tokenInfo, account);
    }
}
