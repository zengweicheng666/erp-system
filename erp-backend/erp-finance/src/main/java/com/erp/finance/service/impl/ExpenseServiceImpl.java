package com.erp.finance.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Expense;
import com.erp.finance.mapper.ExpenseMapper;
import com.erp.finance.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl extends ServiceImpl<ExpenseMapper, Expense> implements ExpenseService {
    @Override
    public PageResult<Expense> listExpenses(FinanceQuery query) {
        LambdaQueryWrapper<Expense> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getExpenseType())) w.eq(Expense::getExpenseType, query.getExpenseType());
        w.orderByDesc(Expense::getCreateTime);
        Page<Expense> p = page(new Page<>(query.getPage(), query.getPageSize()), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
    @Override public boolean createExpense(Expense e) { return save(e); }
}
