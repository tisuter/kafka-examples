package ch.ipt.kafka.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @KafkaListener(topics = Producer.TOPIC_CUSTOMER)
    public void foo(String key, @Payload String value) {
        log.info("Received: {} - {}", key, value);
    }
}
