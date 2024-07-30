package com.click.account.domain.dto.request.friend;

import com.click.account.domain.entity.Friend;
import java.util.UUID;

public record FriendsRequest(
    UUID id,
    String code,
    String img,
    String name
) {
    public Friend toEntity(String account) {
        return Friend.builder()
            .userId(id)
            .userCode(code)
            .userImg(img)
            .userName(name)
            .account(account)
            .build();
    }
}
