package com.click.account.config.apis;

import com.click.account.domain.dto.request.FriendshipRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiFriendship {
    private final FeignFriendship feignFriendship;

    @Async
    public void inviteFriend(String code, FriendshipRequest req) {
        feignFriendship.inviteFriend(code, req);
    }

}
