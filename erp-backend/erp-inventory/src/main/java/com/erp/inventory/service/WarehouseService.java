package com.erp.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.inventory.entity.Warehouse;
import java.util.List;

public interface WarehouseService extends IService<Warehouse> {
    List<Warehouse> listAll();
    boolean createWarehouse(Warehouse warehouse);
    boolean updateWarehouse(Warehouse warehouse);
}
