package com.web.makarova.treasury.feign;


import com.web.makarova.treasury.feign.dto.AssetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "asset-rates",
        path = "${feign.url.local}",
        url = "${feign.path}"
)
public interface RequestRatesClient {
    @GetMapping("/get-all")
    List<AssetResponse> getAllAsset();
}
