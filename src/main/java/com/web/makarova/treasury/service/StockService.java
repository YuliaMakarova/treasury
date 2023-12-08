package com.web.makarova.treasury.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.makarova.treasury.feign.AlphaVantageClient;
import com.web.makarova.treasury.feign.ExchangeRatesClient;
import com.web.makarova.treasury.feign.RequestRatesClient;
import com.web.makarova.treasury.feign.dto.AssetResponse;
import com.web.makarova.treasury.feign.dto.ExchangeRatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
    private final RequestRatesClient requestRatesClient;
    private final AssetPriceService assetPriceService;

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
        assetPriceService.save(ticker, currency, price);
        return price;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void checkAssetFromCore() {
        List<AssetResponse> assetResponses = requestRatesClient.getAllAsset();
//        List<AssetResponse> assetResponses = List.of(new AssetResponse()
//            .setCurrency("RUB")
//            .setTicker("BABA"));
        assetResponses
                .forEach(asset -> {
                    // Представьте, что у вас 10000 активов и по одному из них проходит сбой
                    // стоит один актив того, что операция не выполнится целиком?
                    try {
                        getStockPrice(asset.getBloombergTicker(), asset.getCurrency().getName());
                        //Грех, но пустым оставлять еще больший грех
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }
                });
        //kafka
    }
}

