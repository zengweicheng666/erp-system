package com.erp.production.service.impl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.production.entity.ProductionOrder;
import com.erp.production.mapper.ProductionOrderMapper;
import com.erp.production.service.ProductionOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class ProductionOrderServiceImpl extends ServiceImpl<ProductionOrderMapper, ProductionOrder> implements ProductionOrderService {
    @Override public PageResult<ProductionOrder> listOrders(Long page, Long pageSize) {
        Page<ProductionOrder> p = page(new Page<>(page, pageSize));
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()); }
    @Override public boolean startOrder(Long orderId) { ProductionOrder o = getById(orderId); if (o != null) { o.setStatus(1); return updateById(o); } return false; }
    @Override public boolean completeOrder(Long orderId) { ProductionOrder o = getById(orderId); if (o != null) { o.setStatus(2); return updateById(o); } return false; }
}
