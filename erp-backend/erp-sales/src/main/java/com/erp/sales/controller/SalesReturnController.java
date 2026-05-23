package com.erp.sales.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.sales.dto.SalesOrderQuery;
import com.erp.sales.entity.SalesReturn;
import com.erp.sales.service.SalesReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "销售退货管理")
@RestController
@RequestMapping("/sales/return")
@RequiredArgsConstructor
public class SalesReturnController {
    private final SalesReturnService salesReturnService;

    @Operation(summary = "销售退货列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sales:return:list')")
    public Result<PageResult<SalesReturn>> list(SalesOrderQuery q) { return Result.success(salesReturnService.listReturns(q)); }

    @PostMapping
    @PreAuthorize("hasAuthority('sales:return:add')")
    public Result<Void> add(@RequestBody SalesReturn r) { salesReturnService.createReturn(r); return Result.success(); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sales:return:remove')")
    public Result<Void> remove(@PathVariable Long id) { salesReturnService.removeById(id); return Result.success(); }
}
