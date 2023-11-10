package com.web.makarova.treasury.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="assetprice")
public class AssetPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String bloombergTicker;
    @Column
    private String currency;
    @Column
    private Double price;
    @Column
    private LocalDateTime timestamp;
}
