package com.web.makarova.treasury.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.makarova.treasury.feign.AlphaVantageClient;
import com.web.makarova.treasury.feign.ExchangeRatesClient;
import com.web.makarova.treasury.feign.dto.ExchangeRatesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    @Value("${stock.apiKey}")
    private String apiKey;
    @Value("${stock.function}")
    private String function;

    @Value("${stock.appId}")
    private String appId;
    @Value("${stock.base}")
    private String base;

    // Хорошей практикой является описание сервисов подкладок для клиентов
    // в моменте можно позаниматься обработкой ошибок или фильтрацией данных (навскидку)
    private final AlphaVantageClient alphaVantageClient;
    private final ExchangeRatesClient exchangeRatesClient;

    public BigDecimal getStockPrice(String ticker, String currency) {
        JsonNode response = alphaVantageClient.getStockPrice(function, ticker, apiKey);
        String textPrice = Optional.of(
            response
                .path("Global Quote")
                .path("05. price")
                .asText()
        ).orElseThrow(() -> new RuntimeException(String.format("Цена для актива %s не найдена", ticker)));
        // Не используйте Double для хранения сущностей, связанных с деньгами
        BigDecimal price = new BigDecimal(textPrice);

        if (!currency.equals("USD")) {
            ExchangeRatesResponse exchangeRates = exchangeRatesClient.getExchangeRates(appId, base, currency);
            BigDecimal rate = exchangeRates.getRates().get(currency);
            price = price.multiply(rate);
        }
        return price;
    }
}

