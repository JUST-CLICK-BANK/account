package com.click.account.domain.dto.response;

import com.click.account.domain.entity.Friend;
import java.util.UUID;

public record FriendResponse(
    UUID id,
    String code,
    String img,
    String name,
    Integer rank
) {
    public static FriendResponse from(Friend friend) {
        return new FriendResponse(
            friend.getFriendId(),
            friend.getFriendCode(),
            friend.getFriendImg(),
            friend.getFriendName(),
            friend.getFriendRank()
        );
    }
}
