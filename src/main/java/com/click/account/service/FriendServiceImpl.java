package com.click.account.service;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.dto.response.FriendResponse;
import com.click.account.domain.entity.Friend;
import com.click.account.domain.repository.FriendRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final ApiService apiService;

    @Override
    public List<FriendResponse> getFriends(TokenInfo tokenInfo, String account) {
        List<Friend> friends = apiService.getFriendsInfo(tokenInfo.code(), account);
        if (friends.isEmpty()) throw new IllegalArgumentException();

        friendRepository.saveAll(friends);

        return friends.stream().map(FriendResponse::from).toList();
    }
}
