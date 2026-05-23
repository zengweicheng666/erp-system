package com.erp.sales.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesReturn;
public interface SalesReturnService extends IService<SalesReturn> {
    PageResult<SalesReturn> listReturns(SalesOrderQuery query);
    boolean createReturn(SalesReturn returnOrder);
}
