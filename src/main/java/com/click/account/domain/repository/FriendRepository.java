package com.click.account.domain.repository;

import com.click.account.domain.entity.Friend;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
    Optional<Friend> findById(UUID userId);
    List<Friend> findByAccount(String account);
}
