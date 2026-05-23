package com.erp.finance.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Payable;
import com.erp.finance.mapper.PayableMapper;
import com.erp.finance.service.PayableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PayableServiceImpl extends ServiceImpl<PayableMapper, Payable> implements PayableService {
    @Override
    public PageResult<Payable> listPayables(FinanceQuery query) {
        LambdaQueryWrapper<Payable> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getInvoiceNo())) w.like(Payable::getInvoiceNo, query.getInvoiceNo());
        if (query.getSupplierId() != null) w.eq(Payable::getSupplierId, query.getSupplierId());
        if (query.getStatus() != null) w.eq(Payable::getStatus, query.getStatus());
        w.orderByDesc(Payable::getCreateTime);
        Page<Payable> p = page(new Page<>(query.getPage(), query.getPageSize()), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
    @Override public boolean createPayable(Payable p) { return save(p); }
    @Override
    @Transactional
    public boolean pay(Long payableId, BigDecimal amount) {
        Payable p = getById(payableId);
        if (p == null) return false;
        p.setPaidAmount(p.getPaidAmount().add(amount));
        p.setUnsettledAmount(p.getTotalAmount().subtract(p.getPaidAmount()));
        if (p.getUnsettledAmount().compareTo(BigDecimal.ZERO) <= 0) p.setStatus(2);
        else p.setStatus(1);
        return updateById(p);
    }
}
