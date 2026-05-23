package com.erp.finance.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Receipt;
import com.erp.finance.mapper.ReceiptMapper;
import com.erp.finance.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl extends ServiceImpl<ReceiptMapper, Receipt> implements ReceiptService {
    @Override
    public PageResult<Receipt> listReceipts(FinanceQuery query) {
        Page<Receipt> p = page(new Page<>(query.getPage(), query.getPageSize()), new LambdaQueryWrapper<Receipt>().orderByDesc(Receipt::getCreateTime));
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
    @Override public boolean createReceipt(Receipt r) { return save(r); }
}
