package com.erp.finance.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Receipt;
public interface ReceiptService extends IService<Receipt> {
    PageResult<Receipt> listReceipts(FinanceQuery query);
    boolean createReceipt(Receipt receipt);
}
