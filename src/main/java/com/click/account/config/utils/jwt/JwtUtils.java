package com.click.account.config.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
//    @Value("${jwt.secret}")
    SecretKey publicKey;

    public TokenInfo parseUserToken(String token) {
        Claims claims = (Claims) Jwts
                .parser()
                .verifyWith(publicKey)
                .build()
                .parse(token)
                .getPayload();
        return TokenInfo.from(claims);
    }

    public JwtUtils(
            @Value("${jwt.secret}") String key
    ) {
        this.publicKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        TokenInfo tokenInfo = parseUserToken(token);
        return tokenInfo.name().equals(userDetails.getUsername());
    }
}
