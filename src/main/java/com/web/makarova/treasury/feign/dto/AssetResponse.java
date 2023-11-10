package com.web.makarova.treasury.feign.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetResponse {
    private String Stock;
    private String Currency;
    private String Ticker;
}
