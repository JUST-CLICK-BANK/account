package com.click.account.controller;

import com.click.account.domain.dto.request.GroupAccountMemberRequest;
import com.click.account.service.GroupAccountMemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Accounts/group")
public class GroupAccountMemberController {
    private final GroupAccountMemberService groupAccountMemberService;

    @PostMapping()
    public void saveGroupAccount(List<GroupAccountMemberRequest> req, String account) {
//        groupAccountMemberService.receive(req);
    }

}
