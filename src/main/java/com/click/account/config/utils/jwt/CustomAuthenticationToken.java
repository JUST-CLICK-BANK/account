package com.click.account.config.utils.jwt;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public record CustomAuthenticationToken(
    TokenInfo tokenInfo,
    boolean authenticated
) implements Authentication {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return tokenInfo;
    }

    @Override
    public Object getPrincipal() {
        return tokenInfo;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UsernameNotFoundException("Not Exist User");
    }

    @Override
    public String getName() {
        return tokenInfo().name();
    }

    public TokenInfo tokenInfo() {
        return tokenInfo;
    }
}
