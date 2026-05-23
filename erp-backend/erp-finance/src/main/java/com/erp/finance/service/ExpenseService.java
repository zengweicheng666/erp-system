package com.erp.finance.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Expense;
public interface ExpenseService extends IService<Expense> {
    PageResult<Expense> listExpenses(FinanceQuery query);
    boolean createExpense(Expense expense);
}
