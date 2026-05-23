package com.erp.purchase.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.inventory.dto.StockOperateRequest;
import com.erp.inventory.service.StockService;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseOrder;
import com.erp.purchase.entity.PurchaseOrderItem;
import com.erp.purchase.entity.PurchaseReturn;
import com.erp.purchase.mapper.PurchaseOrderItemMapper;
import com.erp.purchase.mapper.PurchaseReturnMapper;
import com.erp.purchase.service.PurchaseReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PurchaseReturnServiceImpl extends ServiceImpl<PurchaseReturnMapper, PurchaseReturn> implements PurchaseReturnService {
    private final PurchaseOrderItemMapper orderItemMapper;
    private final StockService stockService;

    @Override
    public PageResult<PurchaseReturn> listReturns(OrderQuery query) {
        Page<PurchaseReturn> p = page(new Page<>(query.getPage(), query.getPageSize()), new LambdaQueryWrapper<PurchaseReturn>().orderByDesc(PurchaseReturn::getCreateTime));
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }

    @Override
    @Transactional
    public boolean createReturn(PurchaseReturn returnOrder) {
        save(returnOrder);
        LambdaQueryWrapper<PurchaseOrderItem> w = new LambdaQueryWrapper<>();
        w.eq(PurchaseOrderItem::getOrderId, returnOrder.getOrderId());
        PurchaseOrderItem item = orderItemMapper.selectOne(w);
        if (item != null) {
            StockOperateRequest req = new StockOperateRequest();
            req.setProductId(item.getProductId());
            req.setWarehouseId(1L);
            req.setQuantity(item.getQuantity());
            req.setOrderNo(returnOrder.getReturnNo());
            stockService.stockOut(req);
        }
        return true;
    }
}
