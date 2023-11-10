package com.web.makarova.treasury.service;

import com.web.makarova.treasury.feign.AlphaVantageClient;
import com.web.makarova.treasury.feign.ExchangeRatesClient;
import com.web.makarova.treasury.feign.dto.ExchangeRatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StockService {
    @Value("${stock.apiKey}")
    private String apiKey;
    @Value("${stock.function}")
    private String function;

    @Value("${stock.appId}")
    private String appId;
    @Value("${stock.base}")
    private String base;

    private final AlphaVantageClient alphaVantageClient;
    private final ExchangeRatesClient exchangeRatesClient;

    @Autowired
    public StockService(AlphaVantageClient alphaVantageClient, ExchangeRatesClient exchangeRatesClient) {
        this.alphaVantageClient = alphaVantageClient;
        this.exchangeRatesClient = exchangeRatesClient;
    }

    public Double getStockPrice(String ticker, String currency) {
        Double price;

        Map<String, Map<String, String>> response = alphaVantageClient.getStockPrice(function, ticker, apiKey);
        Map<String, String> globalQuote = response.get("Global Quote");
        price = Double.valueOf(globalQuote.get("05. price"));

        if (!currency.equals("USD")) {
            ExchangeRatesResponse exchangeRates = exchangeRatesClient.getExchangeRates(appId, base, currency);
            Double rate = exchangeRates.getRates().get(currency);
            price *= rate;
        }
        return price;
    }
}

