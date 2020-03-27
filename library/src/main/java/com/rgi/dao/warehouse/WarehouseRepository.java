package com.rgi.dao.warehouse;

import com.rgi.model.warehouse.Warehouse;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {
    Collection<? extends Warehouse> findByProductId(long productId);
}
