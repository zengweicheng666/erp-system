package com.erp.purchase.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseOrder;
import com.erp.purchase.entity.PurchaseOrderItem;
import com.erp.purchase.mapper.PurchaseOrderItemMapper;
import com.erp.purchase.mapper.PurchaseOrderMapper;
import com.erp.purchase.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements PurchaseOrderService {
    private final PurchaseOrderItemMapper itemMapper;

    @Override
    public PageResult<PurchaseOrder> listOrders(OrderQuery query) {
        LambdaQueryWrapper<PurchaseOrder> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getOrderNo())) w.like(PurchaseOrder::getOrderNo, query.getOrderNo());
        if (query.getSupplierId() != null) w.eq(PurchaseOrder::getSupplierId, query.getSupplierId());
        if (query.getStatus() != null) w.eq(PurchaseOrder::getStatus, query.getStatus());
        w.orderByDesc(PurchaseOrder::getCreateTime);
        Page<PurchaseOrder> p = page(new Page<>(query.getPage(), query.getPageSize()), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }

    @Override
    public PurchaseOrder getOrderWithItems(Long orderId) {
        PurchaseOrder order = getById(orderId);
        if (order != null) {
            LambdaQueryWrapper<PurchaseOrderItem> w = new LambdaQueryWrapper<>();
            w.eq(PurchaseOrderItem::getOrderId, orderId);
            order.setItems(itemMapper.selectList(w));
        }
        return order;
    }

    @Override
    @Transactional
    public boolean createOrder(PurchaseOrder order, List<PurchaseOrderItem> items) {
        BigDecimal total = items.stream().map(i -> i.getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
        order.setStatus(0);
        save(order);
        for (PurchaseOrderItem item : items) { item.setOrderId(order.getOrderId()); itemMapper.insert(item); }
        return true;
    }

    @Override
    @Transactional
    public boolean updateOrder(PurchaseOrder order, List<PurchaseOrderItem> items) {
        BigDecimal total = items.stream().map(i -> i.getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
        updateById(order);
        LambdaQueryWrapper<PurchaseOrderItem> w = new LambdaQueryWrapper<>();
        w.eq(PurchaseOrderItem::getOrderId, order.getOrderId());
        itemMapper.delete(w);
        for (PurchaseOrderItem item : items) { item.setOrderId(order.getOrderId()); itemMapper.insert(item); }
        return true;
    }

    @Override
    public boolean submitOrder(Long orderId) {
        PurchaseOrder o = getById(orderId);
        if (o != null) { o.setStatus(1); return updateById(o); }
        return false;
    }

    @Override
    public boolean approveOrder(Long orderId) {
        PurchaseOrder o = getById(orderId);
        if (o != null) { o.setStatus(2); return updateById(o); }
        return false;
    }
}
