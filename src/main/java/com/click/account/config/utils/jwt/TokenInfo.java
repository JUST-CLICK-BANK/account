package com.click.account.config.utils.jwt;

import com.click.account.domain.entity.User;
import io.jsonwebtoken.Claims;

import java.util.UUID;

public record TokenInfo(
        String id,
        String code,
        String img,
        String name,
        Integer rank
) {
    static public TokenInfo from(Claims claims) {
        return new TokenInfo(
                claims.get("id", String.class),
                claims.get("code", String.class),
                claims.get("img", String.class),
                claims.get("name", String.class),
                claims.get("rank", Integer.class)
        );
    }

    public User toEntity() {
        return User.builder()
            .userId(UUID.fromString(id))
            .userNickName(name)
            .userPorfileImg(img)
            .userCode(code)
            .rank(rank)
            .build();
    }
}
