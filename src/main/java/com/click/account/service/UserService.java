package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.response.UserResponse;
import com.click.account.domain.entity.User;
import java.util.UUID;

public interface UserService {
    User getUser(TokenInfo tokenInfo);
    UserResponse getUserInfo(TokenInfo tokenInfo);
}
