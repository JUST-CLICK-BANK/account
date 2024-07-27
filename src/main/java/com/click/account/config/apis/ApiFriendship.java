package com.click.account.config.apis;

import com.click.account.domain.dto.response.FriendResponse;
import com.click.account.domain.entity.Friend;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiFriendship {
    private final FeignFriendship feignFriendship;

    @Async
    public List<FriendResponse> inviteFriend(String code) {
        return feignFriendship.inviteFriend(code);
    }

}
