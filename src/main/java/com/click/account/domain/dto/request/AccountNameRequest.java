package com.click.account.domain.dto.request;

import jakarta.persistence.Column;

public record AccountNameRequest(
        String accountName
) {
}
