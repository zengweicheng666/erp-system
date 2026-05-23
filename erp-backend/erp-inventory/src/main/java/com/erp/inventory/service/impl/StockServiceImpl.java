package com.erp.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.exception.BaseException;
import com.erp.common.result.PageResult;
import com.erp.common.result.ResultCode;
import com.erp.inventory.dto.StockOperateRequest;
import com.erp.inventory.dto.StockQuery;
import com.erp.inventory.entity.Stock;
import com.erp.inventory.entity.StockRecord;
import com.erp.inventory.mapper.StockMapper;
import com.erp.inventory.mapper.StockRecordMapper;
import com.erp.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    private final StockMapper stockMapper;
    private final StockRecordMapper stockRecordMapper;

    @Override
    public PageResult<Stock> listStocks(StockQuery query) {
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        if (query.getProductId() != null) {
            wrapper.eq(Stock::getProductId, query.getProductId());
        }
        if (query.getWarehouseId() != null) {
            wrapper.eq(Stock::getWarehouseId, query.getWarehouseId());
        }

        Page<Stock> page = new Page<>(query.getPage(), query.getPageSize());
        Page<Stock> result = page(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public Stock getStock(Long productId, Long warehouseId) {
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Stock::getProductId, productId);
        wrapper.eq(Stock::getWarehouseId, warehouseId);
        return getOne(wrapper);
    }

    @Override
    @Transactional
    public boolean stockIn(StockOperateRequest request) {
        Stock stock = getStock(request.getProductId(), request.getWarehouseId());
        if (stock == null) {
            stock = new Stock();
            stock.setProductId(request.getProductId());
            stock.setWarehouseId(request.getWarehouseId());
            stock.setQuantity(request.getQuantity());
            stock.setLockedQuantity(0);
            stock.setUnitPrice(request.getUnitPrice());
            stockMapper.insert(stock);
        } else {
            stock.setQuantity(stock.getQuantity() + request.getQuantity());
            stock.setUnitPrice(request.getUnitPrice());
            stockMapper.updateById(stock);
        }

        StockRecord record = createRecord(request, 1);
        stockRecordMapper.insert(record);
        return true;
    }

    @Override
    @Transactional
    public boolean stockOut(StockOperateRequest request) {
        Stock stock = getStock(request.getProductId(), request.getWarehouseId());
        if (stock == null || stock.getQuantity() < request.getQuantity()) {
            throw new BaseException(ResultCode.STOCK_NOT_ENOUGH);
        }
        stock.setQuantity(stock.getQuantity() - request.getQuantity());
        stockMapper.updateById(stock);

        StockRecord record = createRecord(request, 2);
        stockRecordMapper.insert(record);
        return true;
    }

    @Override
    public PageResult<StockRecord> listRecords(StockQuery query) {
        LambdaQueryWrapper<StockRecord> wrapper = new LambdaQueryWrapper<>();
        if (query.getProductId() != null) {
            wrapper.eq(StockRecord::getProductId, query.getProductId());
        }
        if (query.getWarehouseId() != null) {
            wrapper.eq(StockRecord::getWarehouseId, query.getWarehouseId());
        }
        wrapper.orderByDesc(StockRecord::getCreateTime);

        Page<StockRecord> page = new Page<>(query.getPage(), query.getPageSize());
        Page<StockRecord> result = stockRecordMapper.selectPage(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    private StockRecord createRecord(StockOperateRequest request, int type) {
        StockRecord record = new StockRecord();
        record.setProductId(request.getProductId());
        record.setWarehouseId(request.getWarehouseId());
        record.setQuantity(request.getQuantity());
        record.setRecordType(type);
        record.setOrderNo(request.getOrderNo());
        record.setUnitPrice(request.getUnitPrice());
        if (request.getUnitPrice() != null) {
            record.setTotalPrice(request.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        }
        record.setRemark(request.getRemark());
        return record;
    }
}
