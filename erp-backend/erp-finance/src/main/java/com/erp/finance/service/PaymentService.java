package com.erp.finance.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Payment;
public interface PaymentService extends IService<Payment> {
    PageResult<Payment> listPayments(FinanceQuery query);
    boolean createPayment(Payment payment);
}
