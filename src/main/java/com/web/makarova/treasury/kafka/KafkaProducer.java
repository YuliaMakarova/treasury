package com.web.makarova.treasury.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducer {

    private final StreamBridge streamBridge;

    public void sendMessage(String topic, String message) {
        log.info("Sending message to topic {}, body: {}", topic, message);
        streamBridge.send(topic, message);
    }
}
