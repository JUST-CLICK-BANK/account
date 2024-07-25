package com.click.account.config.apis;

import com.click.account.domain.dto.request.FriendshipRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "friendship", url = "34.135.133.145:30000/api/v1invite/friends")
public interface FeignFriendship {

    @PostMapping("/{code}")
    void inviteFriend(
        @PathVariable("code") String code,
        @RequestBody FriendshipRequest req
    );
}
