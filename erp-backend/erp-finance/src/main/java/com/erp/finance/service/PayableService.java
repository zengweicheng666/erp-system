package com.erp.finance.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Payable;
import java.math.BigDecimal;
public interface PayableService extends IService<Payable> {
    PageResult<Payable> listPayables(FinanceQuery query);
    boolean createPayable(Payable payable);
    boolean pay(Long payableId, BigDecimal amount);
}
