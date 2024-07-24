package com.click.account.domain.dao;

import com.click.account.config.utils.jwt.TokenInfo;
import com.click.account.domain.entity.GroupAccountMember;
import com.click.account.domain.repository.GroupAccountMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GroupAccountDaoImpl implements GroupAccountDao{
    private final GroupAccountMemberRepository groupAccountMemberRepository;

    @Override
    public void saveGroupToUser(TokenInfo tokenInfo, String account, UUID userId) {
        boolean checkAdmin = !groupAccountMemberRepository.existsByAccountAndAdminIsTrue(account);

        groupAccountMemberRepository.save(
                GroupAccountMember.builder()
                        .account(account)
                        .userCode(tokenInfo.code())
                        .userNickName(tokenInfo.name())
                        .userPofileImg(tokenInfo.img())
                        .admin(checkAdmin)
                        .build()
        );
    }
}
