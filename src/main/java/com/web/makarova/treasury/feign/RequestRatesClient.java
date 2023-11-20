package com.web.makarova.treasury.feign;


import com.web.makarova.treasury.feign.dto.AssetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "asset-rates",
        path = "${feign.url.local}",
        url = "${feign.path}"
    // В сумме у вас будет: http://api/asset/http://localhost:8080/get-all
    // https://www.youtube.com/watch?v=74t4sH5DM6k
)
public interface RequestRatesClient {
    // Я и по http методу знаю что это get
    // лучше нейминг /list
    @GetMapping("/get-all")
    List<AssetResponse> getAllAsset();
}
