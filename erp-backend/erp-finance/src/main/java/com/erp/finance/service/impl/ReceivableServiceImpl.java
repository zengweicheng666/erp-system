package com.erp.finance.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Receivable;
import com.erp.finance.mapper.ReceivableMapper;
import com.erp.finance.service.ReceivableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReceivableServiceImpl extends ServiceImpl<ReceivableMapper, Receivable> implements ReceivableService {
    @Override
    public PageResult<Receivable> listReceivables(FinanceQuery query) {
        LambdaQueryWrapper<Receivable> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getInvoiceNo())) w.like(Receivable::getInvoiceNo, query.getInvoiceNo());
        if (query.getCustomerId() != null) w.eq(Receivable::getCustomerId, query.getCustomerId());
        if (query.getStatus() != null) w.eq(Receivable::getStatus, query.getStatus());
        w.orderByDesc(Receivable::getCreateTime);
        Page<Receivable> p = page(new Page<>(query.getPage(), query.getPageSize()), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
    @Override public boolean createReceivable(Receivable r) { return save(r); }
    @Override
    @Transactional
    public boolean receive(Long receivableId, BigDecimal amount) {
        Receivable r = getById(receivableId);
        if (r == null) return false;
        r.setPaidAmount(r.getPaidAmount().add(amount));
        r.setUnsettledAmount(r.getTotalAmount().subtract(r.getPaidAmount()));
        if (r.getUnsettledAmount().compareTo(BigDecimal.ZERO) <= 0) r.setStatus(2);
        else r.setStatus(1);
        return updateById(r);
    }
}
