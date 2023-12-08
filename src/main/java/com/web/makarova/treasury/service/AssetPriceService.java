package com.web.makarova.treasury.service;

import com.web.makarova.treasury.entity.AssetPrice;
import com.web.makarova.treasury.repository.AssetPricePagingAndSortingRepository;
import com.web.makarova.treasury.repository.AssetPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AssetPriceService {
    private final AssetPriceRepository assetPriceRepository;
    private final AssetPricePagingAndSortingRepository assetPricePagingAndSortingRepository;

    public Page<AssetPrice> getAssetPrice(int page, String sortBy, String sortOrder, String field, String value) {
        Pageable pageable;
        int size = 10;
        if (sortBy != null) {
            pageable = sortOrder != null && sortOrder.equalsIgnoreCase("desc") ?
                    PageRequest.of(page, size, Sort.by(sortBy).descending()) :
                    PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size);
        }
        if (field != null) {
            BigDecimal bigDecimalValue = null;
            if (field.equals("price"))
                bigDecimalValue = new BigDecimal(value);
            return switch (field.toLowerCase()) {
                case "bloombergticker" -> assetPricePagingAndSortingRepository.
                        findByBloombergTickerContainingIgnoreCase(value, PageRequest.of(page, size));
                case "currency" -> assetPricePagingAndSortingRepository.findByCurrencyContainingIgnoreCase(value,
                        PageRequest.of(page, size));
                case "price" -> assetPricePagingAndSortingRepository.findByPriceContainingIgnoreCase(bigDecimalValue,
                        PageRequest.of(page, size));
                default -> assetPricePagingAndSortingRepository.findAll(PageRequest.of(page, size));
            };
        } else {
            return assetPricePagingAndSortingRepository.findAll(pageable);
        }
    }

    public void save(String bloombergTicker, String currency, BigDecimal price) {
        // Мб mapstruct заюзать?
        AssetPrice assetPrice = new AssetPrice();
        assetPrice.setBloombergTicker(bloombergTicker);
        assetPrice.setCurrency(currency);
        assetPrice.setPrice(price);
        assetPrice.setTimestamp(LocalDateTime.now());
        assetPriceRepository.save(assetPrice);
    }
}
