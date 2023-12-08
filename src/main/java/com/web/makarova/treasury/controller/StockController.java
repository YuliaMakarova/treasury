package com.web.makarova.treasury.controller;

import com.web.makarova.treasury.entity.AssetPrice;
import com.web.makarova.treasury.kafka.KafkaProducer;
import com.web.makarova.treasury.service.AssetPriceService;
import com.web.makarova.treasury.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final KafkaProducer kafkaProducer;
    private final AssetPriceService assetPriceService;

    @GetMapping("/{page}")
    public ResponseEntity<List<AssetPrice>> getAssetPrice(
            @PathVariable int page,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String filteringField,
            @RequestParam(required = false) String filteringValue) {
        List<AssetPrice> assets = assetPriceService.getAssetPrice(page, sortBy, sortOrder, filteringField,
                        filteringValue).getContent();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/countPage")
    public ResponseEntity<Integer> getCountPageForAssets(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String filteringField,
            @RequestParam(required = false) String filteringValue) {
        int countPage = assetPriceService.getAssetPrice(0, sortBy, sortOrder, filteringField, filteringValue)
                .getTotalPages();
        return new ResponseEntity<>(countPage, HttpStatus.OK);
    }

    @PostMapping
    public void savePrice() {
        stockService.checkAssetFromCore();
    }

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