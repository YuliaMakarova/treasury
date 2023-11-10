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
    @Value("7K787PFK934JZ63F")
    private String apiKey;
    @Value("GLOBAL_QUOTE")
    private String function;

    @Value("17bdd6eccfa44586a9ef23c296e5b0de")
    private String appId;
    @Value("USD")
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

