package com.erp.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.inventory.entity.Warehouse;
import com.erp.inventory.mapper.WarehouseMapper;
import com.erp.inventory.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {

    @Override
    public List<Warehouse> listAll() {
        LambdaQueryWrapper<Warehouse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Warehouse::getStatus, 0);
        return list(wrapper);
    }

    @Override
    public boolean createWarehouse(Warehouse warehouse) {
        return save(warehouse);
    }

    @Override
    public boolean updateWarehouse(Warehouse warehouse) {
        return updateById(warehouse);
    }
}
