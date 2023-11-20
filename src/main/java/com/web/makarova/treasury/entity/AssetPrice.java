package com.web.makarova.treasury.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
// Наименование в SQL через разделитель
@Table(name="asset_price")
public class AssetPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String bloombergTicker;
    @Column
    private String currency;
    @Column
    private BigDecimal price;
    @Column
    private LocalDateTime timestamp;
}
