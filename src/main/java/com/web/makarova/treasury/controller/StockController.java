package com.web.makarova.treasury.controller;

import com.web.makarova.treasury.kafka.KafkaProducer;
import com.web.makarova.treasury.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/{ticker}/{currency}")
    public String getStockPrice(@PathVariable String ticker, @PathVariable String currency) {
        BigDecimal stockPrice = stockService.getStockPrice(ticker, currency);

        if (stockPrice != null) {
            return "Stock Price for " + ticker + ": " + stockPrice;
        } else {
            return "Failed to retrieve stock price for " + ticker;
        }
    }
    //http://localhost:8888/stocks/IBM/USD


    @GetMapping("/hello")
    public void helloFromKafka() {
        kafkaProducer.sendMessage("out", "hello");
    }

}