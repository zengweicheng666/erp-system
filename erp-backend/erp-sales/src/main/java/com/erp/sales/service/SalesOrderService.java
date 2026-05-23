package com.erp.sales.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesOrder;
import com.erp.sales.entity.SalesOrderItem;
import java.util.List;
public interface SalesOrderService extends IService<SalesOrder> {
    PageResult<SalesOrder> listOrders(SalesOrderQuery query);
    SalesOrder getOrderWithItems(Long orderId);
    boolean createOrder(SalesOrder order, List<SalesOrderItem> items);
    boolean updateOrder(SalesOrder order, List<SalesOrderItem> items);
    boolean submitOrder(Long orderId);
    boolean approveOrder(Long orderId);
}
