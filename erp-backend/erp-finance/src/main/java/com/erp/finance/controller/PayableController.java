package com.erp.finance.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.finance.dto.FinanceQuery;
import com.erp.finance.entity.Payable;
import com.erp.finance.service.PayableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@Tag(name = "应付账款管理")
@RestController
@RequestMapping("/finance/payable")
@RequiredArgsConstructor
public class PayableController {
    private final PayableService payableService;

    @Operation(summary = "应付账款列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('finance:payable:list')")
    public Result<PageResult<Payable>> list(FinanceQuery q) { return Result.success(payableService.listPayables(q)); }

    @PostMapping
    @PreAuthorize("hasAuthority('finance:payable:add')")
    public Result<Void> add(@RequestBody Payable p) { payableService.createPayable(p); return Result.success(); }

    @Operation(summary = "付款")
    @PutMapping("/pay/{id}")
    @PreAuthorize("hasAuthority('finance:payable:edit')")
    public Result<Void> pay(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        payableService.pay(id, amount);
        return Result.success();
    }
}
