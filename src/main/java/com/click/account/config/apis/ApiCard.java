package com.click.account.config.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiCard {
    private final FeignCard feignCard;

    public void deleteCard(String bearerToken, String account) {
        feignCard.deleteCard(bearerToken, account);
    }
}
