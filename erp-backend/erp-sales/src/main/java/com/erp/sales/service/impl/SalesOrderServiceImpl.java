package com.erp.sales.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesOrder;
import com.erp.sales.entity.SalesOrderItem;
import com.erp.sales.mapper.SalesOrderItemMapper;
import com.erp.sales.mapper.SalesOrderMapper;
import com.erp.sales.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderServiceImpl extends ServiceImpl<SalesOrderMapper, SalesOrder> implements SalesOrderService {
    private final SalesOrderItemMapper itemMapper;

    @Override
    public PageResult<SalesOrder> listOrders(SalesOrderQuery query) {
        LambdaQueryWrapper<SalesOrder> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getOrderNo())) w.like(SalesOrder::getOrderNo, query.getOrderNo());
        if (query.getCustomerId() != null) w.eq(SalesOrder::getCustomerId, query.getCustomerId());
        if (query.getStatus() != null) w.eq(SalesOrder::getStatus, query.getStatus());
        w.orderByDesc(SalesOrder::getCreateTime);
        Page<SalesOrder> p = page(new Page<>(query.getPage(), query.getPageSize()), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }

    @Override
    public SalesOrder getOrderWithItems(Long orderId) {
        SalesOrder order = getById(orderId);
        if (order != null) {
            LambdaQueryWrapper<SalesOrderItem> w = new LambdaQueryWrapper<>();
            w.eq(SalesOrderItem::getOrderId, orderId);
            order.setItems(itemMapper.selectList(w));
        }
        return order;
    }

    @Override
    @Transactional
    public boolean createOrder(SalesOrder order, List<SalesOrderItem> items) {
        BigDecimal total = items.stream().map(SalesOrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
        order.setStatus(0);
        save(order);
        for (SalesOrderItem item : items) { item.setOrderId(order.getOrderId()); itemMapper.insert(item); }
        return true;
    }

    @Override
    @Transactional
    public boolean updateOrder(SalesOrder order, List<SalesOrderItem> items) {
        BigDecimal total = items.stream().map(SalesOrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
        updateById(order);
        LambdaQueryWrapper<SalesOrderItem> w = new LambdaQueryWrapper<>();
        w.eq(SalesOrderItem::getOrderId, order.getOrderId());
        itemMapper.delete(w);
        for (SalesOrderItem item : items) { item.setOrderId(order.getOrderId()); itemMapper.insert(item); }
        return true;
    }

    @Override
    public boolean submitOrder(Long orderId) {
        SalesOrder o = getById(orderId);
        if (o != null) { o.setStatus(1); return updateById(o); }
        return false;
    }

    @Override
    public boolean approveOrder(Long orderId) {
        SalesOrder o = getById(orderId);
        if (o != null) { o.setStatus(2); return updateById(o); }
        return false;
    }
}
