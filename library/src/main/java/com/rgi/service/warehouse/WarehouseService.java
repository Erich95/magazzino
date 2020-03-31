package com.rgi.service.warehouse;

import com.rgi.dao.warehouse.WarehouseRepository;
import com.rgi.model.warehouse.Warehouse;
import com.rgi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductService productService;

    public Collection<? extends Warehouse> warehouseInfos() {
        return (Collection<? extends Warehouse>)warehouseRepository.findAll();
    }

//    public Collection<? extends Warehouse> warehouseProductInfo(long id) {
//        return warehouseRepository.
//    }

    public Optional<Warehouse> warehouseInfo(long id) {
        return warehouseRepository.findById(id);
    }

    public boolean addWarehouseInfo(Warehouse warehouse) {
        if (warehouse.getBasePrice() > 0 && warehouse.getProduct() != null && warehouse.getQuantity() >= 0.0) {
            warehouseRepository.save(warehouse);
        }
        return true;
    }

    public void updateWarehouseInfo(long id, Warehouse warehouse) {
        Optional<Warehouse> toDelete = warehouseInfo(warehouse.getId());
        if (warehouse.getBasePrice() > 0 && warehouse.getProduct() != null && warehouse.getQuantity() >= 0.0) {
            toDelete.ifPresent(w -> warehouseRepository.save(warehouse));
        }
    }

    public void deleteWarehouseInfo(long id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        warehouse.ifPresent(value -> warehouseRepository.delete(value));
    }
}
