package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.response.FriendResponse;
import com.click.account.domain.entity.Friend;
import com.click.account.domain.repository.FriendRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final ApiService apiService;

    @Override
    public List<FriendResponse> getFriends(TokenInfo tokenInfo, String account) {
        if(tokenInfo == null) throw new IllegalArgumentException("Not Found User");

        List<Friend> friends = friendRepository.findByAccount(account);
        return friends.stream().map(FriendResponse::from).collect(Collectors.toList());
    }

    @Override
    public void save(String code, String account) {
        List<Friend> friendResponses = apiService.getFriendsInfo(code, account);
        if (friendResponses.isEmpty()) throw new IllegalArgumentException();
        friendRepository.saveAll(friendResponses);
    }
}
