package com.erp.finance.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Expense;
import com.erp.finance.service.ExpenseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "费用管理")
@RestController
@RequestMapping("/finance/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('finance:expense:list')")
    public Result<PageResult<Expense>> list(FinanceQuery q) { return Result.success(expenseService.listExpenses(q)); }
    @PostMapping
    @PreAuthorize("hasAuthority('finance:expense:add')")
    public Result<Void> add(@RequestBody Expense e) { expenseService.createExpense(e); return Result.success(); }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('finance:expense:remove')")
    public Result<Void> remove(@PathVariable Long id) { expenseService.removeById(id); return Result.success(); }
}
