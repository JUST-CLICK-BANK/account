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
        friends.forEach(friend -> System.out.println(friend.getUserCode()));
        return friends.stream().map(FriendResponse::from).collect(Collectors.toList());
    }

    @Override
    public void save(String code, String account) {
        List<Friend> friends = apiService.getFriendsInfo(code, account);
        friends.forEach(friend -> System.out.println(friend.getAccount()));
        if (friends.isEmpty()) throw new IllegalArgumentException();
        List<Friend> test = friendRepository.saveAll(friends);
        test.forEach(tes -> System.out.println(tes.getUserCode()));
    }
}
