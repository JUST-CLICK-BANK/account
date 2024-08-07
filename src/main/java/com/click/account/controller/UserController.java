package com.click.account.controller;

import com.click.account.config.utils.jwt.JwtUtils;
import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.response.UserResponse;
import com.click.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts/users")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*",
    methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
    })
public class UserController {

    private final UserService userService;

    @GetMapping()
    public UserResponse getUser(
        TokenInfo tokenInfo
    ) {
        return userService.getUserInfo(tokenInfo);
    }
}
