package com.web.makarova.treasury.service;

import com.web.makarova.treasury.entity.AssetPrice;
import com.web.makarova.treasury.feign.RequestRatesClient;
import com.web.makarova.treasury.feign.dto.AssetResponse;
import com.web.makarova.treasury.repository.AssetPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetPriceService {
    private final AssetPriceRepository assetPriceRepository;
    private final RequestRatesClient requestRatesClient;
    private final StockService stockService;


    public void saveAssetPrice(String bloombergTicker, String currency, Double price) {
        AssetPrice assetPrice = new AssetPrice();
        assetPrice.setBloombergTicker(bloombergTicker);
        assetPrice.setCurrency(currency);
        assetPrice.setPrice(price);
        assetPrice.setTimestamp(LocalDateTime.now());
        assetPriceRepository.save(assetPrice);
    }

    @Scheduled(cron = "0 0 12 * * ?")
    //@Scheduled(fixedRate = 9000)
    public List<Double> checkAssetFromCore() {
        List<AssetResponse> assetResponses = requestRatesClient.getAllAsset();
        return assetResponses.stream()
                .map(asset -> stockService.getStockPrice(asset.getTicker(), asset.getCurrency()))
                .toList();
        //kafka
    }
}
