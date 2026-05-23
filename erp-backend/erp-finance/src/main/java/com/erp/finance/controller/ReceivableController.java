package com.erp.finance.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Receivable;
import com.erp.finance.service.ReceivableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@Tag(name = "应收账款管理")
@RestController
@RequestMapping("/finance/receivable")
@RequiredArgsConstructor
public class ReceivableController {
    private final ReceivableService receivableService;

    @Operation(summary = "应收账款列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('finance:receivable:list')")
    public Result<PageResult<Receivable>> list(FinanceQuery q) { return Result.success(receivableService.listReceivables(q)); }

    @PostMapping
    @PreAuthorize("hasAuthority('finance:receivable:add')")
    public Result<Void> add(@RequestBody Receivable r) { receivableService.createReceivable(r); return Result.success(); }

    @Operation(summary = "收款")
    @PutMapping("/receive/{id}")
    @PreAuthorize("hasAuthority('finance:receivable:edit')")
    public Result<Void> receive(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        receivableService.receive(id, amount);
        return Result.success();
    }
}
