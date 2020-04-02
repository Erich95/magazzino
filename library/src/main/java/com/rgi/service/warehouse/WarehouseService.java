package com.rgi.service.warehouse;

import com.rgi.dao.warehouse.WarehouseRepository;
import com.rgi.model.warehouse.Warehouse;
import com.rgi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
        boolean insertable = true;
        boolean updateQuantity = false;
        List<Warehouse> warehouseList = (List<Warehouse>) warehouseInfos();

        for (int i = 0; i < warehouseList.size(); i++) {
            if (warehouseList.get(i).getProduct().getName().equalsIgnoreCase(warehouse.getProduct().getName())) {
                warehouseList.get(i).setQuantity(warehouseList.get(i).getQuantity() + warehouse.getQuantity());
                warehouseRepository.save(warehouseList.get(i));
                updateQuantity = true;
                insertable = false;
                break;
            }
        }
        if (insertable) {
            warehouseRepository.save(warehouse);
        }
        if (updateQuantity) {
            insertable = false;
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
