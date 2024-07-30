package com.click.account.config.apis;

import com.click.account.domain.dto.response.FriendResponse;
import com.click.account.domain.entity.Friend;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "friendship", url = "34.135.133.145:30000/api/v1/friends")
public interface FeignFriendship {

    @GetMapping("/invite/{code}")
    List<FriendResponse> inviteFriend(@PathVariable("code") String code);
}
