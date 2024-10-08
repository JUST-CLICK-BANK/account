package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.response.FriendResponse;
import java.util.List;

public interface FriendService {
    List<FriendResponse> getFriends(TokenInfo tokenInfo, String account);
}
