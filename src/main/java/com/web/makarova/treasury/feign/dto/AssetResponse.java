package com.web.makarova.treasury.feign.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AssetResponse {
    // Зачем с заглавных?
    private String Stock;
    private String Currency;
    private String Ticker;
}
