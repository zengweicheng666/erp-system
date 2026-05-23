package com.erp.inventory.controller;

import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.inventory.dto.StockOperateRequest;
import com.erp.inventory.dto.StockQuery;
import com.erp.inventory.entity.Stock;
import com.erp.inventory.entity.StockRecord;
import com.erp.inventory.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "库存管理")
@RestController
@RequestMapping("/inventory/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @Operation(summary = "分页查询库存")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('inventory:stock:list')")
    public Result<PageResult<Stock>> list(StockQuery query) {
        return Result.success(stockService.listStocks(query));
    }

    @Operation(summary = "入库")
    @PostMapping("/in")
    @PreAuthorize("hasAuthority('inventory:stock:in')")
    public Result<Void> stockIn(@Valid @RequestBody StockOperateRequest request) {
        stockService.stockIn(request);
        return Result.success();
    }

    @Operation(summary = "出库")
    @PostMapping("/out")
    @PreAuthorize("hasAuthority('inventory:stock:out')")
    public Result<Void> stockOut(@Valid @RequestBody StockOperateRequest request) {
        stockService.stockOut(request);
        return Result.success();
    }

    @Operation(summary = "库存流水")
    @GetMapping("/records")
    @PreAuthorize("hasAuthority('inventory:stock:list')")
    public Result<PageResult<StockRecord>> records(StockQuery query) {
        return Result.success(stockService.listRecords(query));
    }
}
