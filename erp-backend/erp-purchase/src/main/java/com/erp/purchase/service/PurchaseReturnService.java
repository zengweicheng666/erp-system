package com.erp.purchase.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseReturn;
public interface PurchaseReturnService extends IService<PurchaseReturn> {
    PageResult<PurchaseReturn> listReturns(OrderQuery query);
    boolean createReturn(PurchaseReturn returnOrder);
}
