package com.web.makarova.treasury.feign;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "alpha-vantage", url = "${feign.url.alpha}")
public interface AlphaVantageClient {
    @GetMapping("/query")
    JsonNode getStockPrice(
            @RequestParam("function") String function,
            @RequestParam("symbol") String symbol,
            @RequestParam("apikey") String apiKey
    );
    //это что за пиздос?
}
