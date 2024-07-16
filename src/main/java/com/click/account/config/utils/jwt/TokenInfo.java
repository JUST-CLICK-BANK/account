package com.click.account.config.utils.jwt;

import io.jsonwebtoken.Claims;

import java.util.UUID;

public record TokenInfo(
        UUID id,
        String code,
        String img,
        String name,
        Integer rank
) {
    static public TokenInfo from(Claims claims) {
        return new TokenInfo(
                claims.get("id", UUID.class),
                claims.get("code", String.class),
                claims.get("img", String.class),
                claims.get("name", String.class),
                claims.get("rank", Integer.class)
        );
    }
}
