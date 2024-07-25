package com.click.account.service;

import com.click.account.config.kafka.dto.KafkaStatus;
import com.click.account.domain.dto.request.GroupAccountMemberRequest;
import com.click.account.domain.dto.response.GroupAccountMemberResponse;
import java.util.List;

public interface GroupAccountMemberService {
    void receive(KafkaStatus<List<GroupAccountMemberRequest>> req);
}
