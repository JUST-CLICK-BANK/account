package com.click.account.config.kafka.dto;

public record KafkaStatus<T>(
    T data, String status
) {

}
