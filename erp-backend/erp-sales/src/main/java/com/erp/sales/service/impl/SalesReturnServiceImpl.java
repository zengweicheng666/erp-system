package com.erp.sales.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.inventory.dto.StockOperateRequest;
import com.erp.inventory.service.StockService;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesOrderItem;
import com.erp.sales.entity.SalesReturn;
import com.erp.sales.mapper.SalesOrderItemMapper;
import com.erp.sales.mapper.SalesReturnMapper;
import com.erp.sales.service.SalesReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SalesReturnServiceImpl extends ServiceImpl<SalesReturnMapper, SalesReturn> implements SalesReturnService {
    private final SalesOrderItemMapper orderItemMapper;
    private final StockService stockService;

    @Override
    public PageResult<SalesReturn> listReturns(SalesOrderQuery query) {
        Page<SalesReturn> p = page(new Page<>(query.getPage(), query.getPageSize()), new LambdaQueryWrapper<SalesReturn>().orderByDesc(SalesReturn::getCreateTime));
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }

    @Override
    @Transactional
    public boolean createReturn(SalesReturn returnOrder) {
        save(returnOrder);
        LambdaQueryWrapper<SalesOrderItem> w = new LambdaQueryWrapper<>();
        w.eq(SalesOrderItem::getOrderId, returnOrder.getOrderId());
        SalesOrderItem item = orderItemMapper.selectOne(w);
        if (item != null) {
            StockOperateRequest req = new StockOperateRequest();
            req.setProductId(item.getProductId());
            req.setWarehouseId(1L);
            req.setQuantity(item.getQuantity());
            req.setOrderNo(returnOrder.getReturnNo());
            stockService.stockIn(req);
        }
        return true;
    }
}
