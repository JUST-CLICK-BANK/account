package com.click.account.service;

import com.click.account.config.kafka.dto.KafkaStatus;
import com.click.account.domain.dto.request.GroupAccountMemberRequest;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.repository.GroupAccountMemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupAccountMemberServiceImpl implements GroupAccountMemberService{
    private final GroupAccountMemberRepository groupAccountMemberRepository;

    @Override
    @KafkaListener(topics = "friendList-topic")
    public void receive(KafkaStatus<List<GroupAccountMemberRequest>> req) {
        boolean checkAdmin = !groupAccountMemberRepository.existsByAccountAndAdminIsTrue(req.data().get(0).account());
        List<GroupAccountMember> groupAccountMembers = new ArrayList<>();

        req.data().forEach(member -> {
            GroupAccountMember request = member.toEntity(req.data().get(0).account(), checkAdmin);
            groupAccountMembers.add(request);
        });
        groupAccountMemberRepository.saveAll(groupAccountMembers);
    }
}
