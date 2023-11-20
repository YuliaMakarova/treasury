package com.web.makarova.treasury.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer implements Consumer<String> {

    //В качестве примера
    @Override
    public void accept(String s) {
        log.info("Got some msg: {}", s);
    }
}
