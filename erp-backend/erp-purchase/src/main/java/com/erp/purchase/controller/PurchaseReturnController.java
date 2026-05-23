package com.erp.purchase.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.OrderQuery;
import com.erp.purchase.entity.PurchaseReturn;
import com.erp.purchase.service.PurchaseReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "采购退货管理")
@RestController
@RequestMapping("/purchase/return")
@RequiredArgsConstructor
public class PurchaseReturnController {
    private final PurchaseReturnService purchaseReturnService;

    @Operation(summary = "采购退货列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('purchase:return:list')")
    public Result<PageResult<PurchaseReturn>> list(OrderQuery q) { return Result.success(purchaseReturnService.listReturns(q)); }

    @PostMapping
    @PreAuthorize("hasAuthority('purchase:return:add')")
    public Result<Void> add(@RequestBody PurchaseReturn r) { purchaseReturnService.createReturn(r); return Result.success(); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('purchase:return:remove')")
    public Result<Void> remove(@PathVariable Long id) { purchaseReturnService.removeById(id); return Result.success(); }
}
