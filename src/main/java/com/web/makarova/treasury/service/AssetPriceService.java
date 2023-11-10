package com.web.makarova.treasury.service;

import com.web.makarova.treasury.entity.AssetPrice;
import com.web.makarova.treasury.repository.AssetPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssetPriceService {
    private final AssetPriceRepository assetPriceRepository;

    @Autowired
    public AssetPriceService(AssetPriceRepository assetPriceRepository) {
        this.assetPriceRepository = assetPriceRepository;
    }

    public void saveAssetPrice(String bloombergTicker, String currency, Double price) {
        AssetPrice assetPrice = new AssetPrice();
        assetPrice.setBloombergTicker(bloombergTicker);
        assetPrice.setCurrency(currency);
        assetPrice.setPrice(price);
        assetPrice.setTimestamp(LocalDateTime.now());
        assetPriceRepository.save(assetPrice);
    }
}
