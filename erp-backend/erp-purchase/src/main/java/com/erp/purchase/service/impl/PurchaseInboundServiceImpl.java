package com.erp.purchase.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.inventory.dto.StockOperateRequest;
import com.erp.inventory.service.StockService;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseInbound;
import com.erp.purchase.entity.PurchaseOrder;
import com.erp.purchase.entity.PurchaseOrderItem;
import com.erp.purchase.mapper.PurchaseInboundMapper;
import com.erp.purchase.mapper.PurchaseOrderItemMapper;
import com.erp.purchase.service.PurchaseInboundService;
import com.erp.purchase.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseInboundServiceImpl extends ServiceImpl<PurchaseInboundMapper, PurchaseInbound> implements PurchaseInboundService {
    private final PurchaseOrderService purchaseOrderService;
    private final PurchaseOrderItemMapper orderItemMapper;
    private final StockService stockService;

    @Override
    public PageResult<PurchaseInbound> listInbounds(OrderQuery query) {
        Page<PurchaseInbound> p = page(new Page<>(query.getPage(), query.getPageSize()), new LambdaQueryWrapper<PurchaseInbound>().orderByDesc(PurchaseInbound::getCreateTime));
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }

    @Override
    @Transactional
    public boolean inbound(Long orderId, Long warehouseId, String remark) {
        PurchaseOrder order = purchaseOrderService.getById(orderId);
        LambdaQueryWrapper<PurchaseOrderItem> w = new LambdaQueryWrapper<>();
        w.eq(PurchaseOrderItem::getOrderId, orderId);
        List<PurchaseOrderItem> items = orderItemMapper.selectList(w);
        for (PurchaseOrderItem item : items) {
            StockOperateRequest req = new StockOperateRequest();
            req.setProductId(item.getProductId());
            req.setWarehouseId(warehouseId);
            req.setQuantity(item.getQuantity());
            req.setUnitPrice(item.getUnitPrice());
            req.setOrderNo(order.getOrderNo());
            req.setRemark(remark);
            stockService.stockIn(req);
            item.setReceivedQty(item.getQuantity());
            orderItemMapper.updateById(item);
        }
        PurchaseInbound inbound = new PurchaseInbound();
        inbound.setOrderId(orderId);
        inbound.setWarehouseId(warehouseId);
        inbound.setInboundDate(LocalDateTime.now());
        inbound.setOrderNo(order.getOrderNo());
        inbound.setRemark(remark);
        order.setStatus(3);
        purchaseOrderService.updateById(order);
        return save(inbound);
    }
}
