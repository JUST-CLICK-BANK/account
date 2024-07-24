package com.click.account.domain.repository;

import com.click.account.domain.entity.GroupAccountMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroupAccountMemberRepository extends JpaRepository<GroupAccountMember, Long> {
    boolean existsByAccountAndAdminIsTrue(String account);
}
