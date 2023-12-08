package com.web.makarova.treasury.repository;

import com.web.makarova.treasury.entity.AssetPrice;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface AssetPricePagingAndSortingRepository extends PagingAndSortingRepository<AssetPrice, Long> {
    Page<AssetPrice> findByBloombergTickerContainingIgnoreCase(@Param("bloombergTicker") String bloombergTicker,
                                                               Pageable pageable);
    Page<AssetPrice> findByCurrencyContainingIgnoreCase(@Param("currency") String currency, Pageable pageable);
    Page<AssetPrice> findByPriceContainingIgnoreCase(@Param("price") BigDecimal price, Pageable pageable);
}
