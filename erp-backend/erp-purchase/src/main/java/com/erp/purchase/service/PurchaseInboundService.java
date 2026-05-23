package com.erp.purchase.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseInbound;
public interface PurchaseInboundService extends IService<PurchaseInbound> {
    PageResult<PurchaseInbound> listInbounds(OrderQuery query);
    boolean inbound(Long orderId, Long warehouseId, String remark);
}
