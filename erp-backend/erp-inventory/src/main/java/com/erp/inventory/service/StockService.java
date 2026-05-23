package com.erp.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.inventory.dto.StockOperateRequest;
import com.erp.inventory.dto.StockQuery;
import com.erp.inventory.entity.Stock;
import com.erp.inventory.entity.StockRecord;
import com.erp.common.result.PageResult;

public interface StockService extends IService<Stock> {
    PageResult<Stock> listStocks(StockQuery query);
    Stock getStock(Long productId, Long warehouseId);
    boolean stockIn(StockOperateRequest request);
    boolean stockOut(StockOperateRequest request);
    PageResult<StockRecord> listRecords(StockQuery query);
}
