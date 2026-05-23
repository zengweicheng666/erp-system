package com.erp.sales.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesOutbound;
public interface SalesOutboundService extends IService<SalesOutbound> {
    PageResult<SalesOutbound> listOutbounds(SalesOrderQuery query);
    boolean outbound(Long orderId, Long warehouseId, String remark);
}
