package com.web.makarova.treasury.feign.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AssetResponse {
    private Long id;
    private Exchange exchange;
    private Currency currency;
    private String isin;
    private String bloombergTicker;
    private String name;

    @Getter
    @Setter
    public static class Currency {
        private Long id;
        private String code;
        private String name;
    }

    @Getter
    @Setter
    public static class Exchange {
        private Long id;
        private String name;
        private String code;
    }
}
