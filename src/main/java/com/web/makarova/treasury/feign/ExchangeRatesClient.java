package com.web.makarova.treasury.feign;

import com.web.makarova.treasury.feign.dto.ExchangeRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-rates", url = "${feign.url.rates}")
public interface ExchangeRatesClient {
    @GetMapping("/api/latest.json?app_id={appId}&base={base}&symbols={symbols}")
    ExchangeRatesResponse getExchangeRates(@PathVariable("appId") String appId,
                                           @PathVariable("base") String base,
                                           @PathVariable("symbols") String symbols);
}
