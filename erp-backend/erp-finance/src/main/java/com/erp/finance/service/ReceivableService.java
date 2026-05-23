package com.erp.finance.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Receivable;
import java.math.BigDecimal;
public interface ReceivableService extends IService<Receivable> {
    PageResult<Receivable> listReceivables(FinanceQuery query);
    boolean createReceivable(Receivable receivable);
    boolean receive(Long receivableId, BigDecimal amount);
}
