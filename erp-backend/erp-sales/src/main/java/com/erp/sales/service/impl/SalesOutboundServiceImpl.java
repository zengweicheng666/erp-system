package com.erp.sales.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.inventory.dto.StockOperateRequest;
import com.erp.inventory.service.StockService;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesOrder;
import com.erp.sales.entity.SalesOrderItem;
import com.erp.sales.entity.SalesOutbound;
import com.erp.sales.mapper.SalesOrderItemMapper;
import com.erp.sales.mapper.SalesOutboundMapper;
import com.erp.sales.service.SalesOrderService;
import com.erp.sales.service.SalesOutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOutboundServiceImpl extends ServiceImpl<SalesOutboundMapper, SalesOutbound> implements SalesOutboundService {
    private final SalesOrderService salesOrderService;
    private final SalesOrderItemMapper orderItemMapper;
    private final StockService stockService;

    @Override
    public PageResult<SalesOutbound> listOutbounds(SalesOrderQuery query) {
        Page<SalesOutbound> p = page(new Page<>(query.getPage(), query.getPageSize()), new LambdaQueryWrapper<SalesOutbound>().orderByDesc(SalesOutbound::getCreateTime));
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }

    @Override
    @Transactional
    public boolean outbound(Long orderId, Long warehouseId, String remark) {
        SalesOrder order = salesOrderService.getById(orderId);
        LambdaQueryWrapper<SalesOrderItem> w = new LambdaQueryWrapper<>();
        w.eq(SalesOrderItem::getOrderId, orderId);
        List<SalesOrderItem> items = orderItemMapper.selectList(w);
        for (SalesOrderItem item : items) {
            StockOperateRequest req = new StockOperateRequest();
            req.setProductId(item.getProductId());
            req.setWarehouseId(warehouseId);
            req.setQuantity(item.getQuantity());
            req.setUnitPrice(item.getUnitPrice());
            req.setOrderNo(order.getOrderNo());
            req.setRemark(remark);
            stockService.stockOut(req);
            item.setDeliveredQty(item.getQuantity());
            orderItemMapper.updateById(item);
        }
        SalesOutbound outbound = new SalesOutbound();
        outbound.setOrderId(orderId);
        outbound.setWarehouseId(warehouseId);
        outbound.setOutboundDate(LocalDateTime.now());
        outbound.setOrderNo(order.getOrderNo());
        outbound.setRemark(remark);
        order.setStatus(3);
        salesOrderService.updateById(order);
        return save(outbound);
    }
}
