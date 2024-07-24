package com.click.account.config.kafka.producer;

import com.click.account.config.kafka.dto.GroupAccountDTO;
import com.click.account.config.kafka.dto.KafkaStatus;
import com.click.account.domain.dto.request.GroupAccountMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, KafkaStatus<GroupAccountDTO>> kafkaTemplate;
//    @Value("${kafka.product.name}") private final String name;

    @Bean
    private NewTopic newTopic(){
        return new NewTopic("friendship-topic", 1, (short) 1);
    }

    public void send(GroupAccountDTO groupAccountDTO, String topic) {
        KafkaStatus<GroupAccountDTO> kafkaStatus = new KafkaStatus<>(groupAccountDTO, "group");
        log.info("{} 보내는 데이터 {}", topic, groupAccountDTO);
        kafkaTemplate.send("friendship-topic", kafkaStatus);
    }
}
