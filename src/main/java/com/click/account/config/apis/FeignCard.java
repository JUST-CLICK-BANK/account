package com.click.account.config.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card-service", url = "https://just-click.shop/api/v1/cards")
public interface FeignCard {

    @DeleteMapping("/account")
    void deleteCard(
        @RequestHeader("Authorization") String bearerToken,
        @RequestParam("account") String account
    );
}
