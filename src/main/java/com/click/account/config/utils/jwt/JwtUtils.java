package com.click.account.config.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;

public class JwtUtils {
    @Value("${jwt.secret}")
    private SecretKey publicKey;

    public TokenInfo parseUserToken(String token) {
        Claims claims = (Claims) Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parse(token)
                .getPayload();
        return TokenInfo.from(claims);
    }
}
