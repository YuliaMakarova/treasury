package com.web.makarova.treasury.controller;

import com.web.makarova.treasury.kafka.KafkaProducer;
import com.web.makarova.treasury.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public StockController(StockService stockService, KafkaProducer kafkaProducer) {
        this.stockService = stockService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/{ticker}/{currency}")
    public String getStockPrice(@PathVariable String ticker, @PathVariable String currency) {
        Double stockPrice = stockService.getStockPrice(ticker, currency);

        if (stockPrice != null) {
            return "Stock Price for " + ticker + ": " + stockPrice;
        } else {
            return "Failed to retrieve stock price for " + ticker;
        }
    }
    //http://localhost:8888/stocks/IBM/USD


    @GetMapping("/hello")
    public void helloFromKafka(){
        kafkaProducer.sendMessage("123", "hello");
    }

}

