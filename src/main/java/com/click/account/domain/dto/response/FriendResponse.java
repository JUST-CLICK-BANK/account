package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Friend;
import java.util.UUID;

public record FriendResponse(
    UUID id,
    String code,
    String img,
    String name
) {
    public static FriendResponse from(Friend friend) {
        return new FriendResponse(
            friend.getUserId(),
            friend.getUserCode(),
            friend.getUserImg(),
            friend.getUserName()
        );
    }
}
