package com.erp.finance.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Receipt;
import com.erp.finance.service.ReceiptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "收款单管理")
@RestController
@RequestMapping("/finance/receipt")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('finance:receipt:list')")
    public Result<PageResult<Receipt>> list(FinanceQuery q) { return Result.success(receiptService.listReceipts(q)); }
    @PostMapping
    @PreAuthorize("hasAuthority('finance:receipt:add')")
    public Result<Void> add(@RequestBody Receipt r) { receiptService.createReceipt(r); return Result.success(); }
}
