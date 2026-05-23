package com.erp.purchase.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseOrder;
import com.erp.purchase.entity.PurchaseOrderItem;
import java.util.List;
public interface PurchaseOrderService extends IService<PurchaseOrder> {
    PageResult<PurchaseOrder> listOrders(OrderQuery query);
    PurchaseOrder getOrderWithItems(Long orderId);
    boolean createOrder(PurchaseOrder order, List<PurchaseOrderItem> items);
    boolean updateOrder(PurchaseOrder order, List<PurchaseOrderItem> items);
    boolean submitOrder(Long orderId);
    boolean approveOrder(Long orderId);
}
