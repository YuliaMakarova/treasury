package com.web.makarova.treasury.repository;

import com.web.makarova.treasury.entity.AssetPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetPriceRepository extends CrudRepository<AssetPrice, Long> {
}
