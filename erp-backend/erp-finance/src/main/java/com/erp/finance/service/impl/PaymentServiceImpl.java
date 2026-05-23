package com.erp.finance.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Payment;
import com.erp.finance.mapper.PaymentMapper;
import com.erp.finance.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {
    @Override
    public PageResult<Payment> listPayments(FinanceQuery query) {
        Page<Payment> p = page(new Page<>(query.getPage(), query.getPageSize()), new LambdaQueryWrapper<Payment>().orderByDesc(Payment::getCreateTime));
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
    @Override public boolean createPayment(Payment p) { return save(p); }
}
