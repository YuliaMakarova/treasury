package com.web.makarova.treasury.service;

import com.web.makarova.treasury.entity.AssetPrice;
import com.web.makarova.treasury.feign.RequestRatesClient;
import com.web.makarova.treasury.feign.dto.AssetResponse;
import com.web.makarova.treasury.repository.AssetPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssetPriceService {
    private final AssetPriceRepository assetPriceRepository;
    private final RequestRatesClient requestRatesClient;
    private final StockService stockService;


    public void save(String bloombergTicker, String currency, BigDecimal price) {
        // Мб mapstruct заюзать?
        AssetPrice assetPrice = new AssetPrice();
        assetPrice.setBloombergTicker(bloombergTicker);
        assetPrice.setCurrency(currency);
        assetPrice.setPrice(price);
        assetPrice.setTimestamp(LocalDateTime.now());
        assetPriceRepository.save(assetPrice);
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
                    stockService.getStockPrice(asset.getBloombergTicker(), asset.getCurrency().getName());
                    //Грех, но пустым оставлять еще больший грех
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            });
        //kafka
    }
}
