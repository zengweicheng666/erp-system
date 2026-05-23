package com.erp.inventory.controller;

import com.erp.common.result.Result;
import com.erp.inventory.entity.Warehouse;
import com.erp.inventory.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "仓库管理")
@RestController
@RequestMapping("/inventory/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Operation(summary = "获取仓库列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('inventory:warehouse:list')")
    public Result<List<Warehouse>> list() {
        return Result.success(warehouseService.listAll());
    }

    @Operation(summary = "新增仓库")
    @PostMapping
    @PreAuthorize("hasAuthority('inventory:warehouse:add')")
    public Result<Void> add(@RequestBody Warehouse warehouse) {
        warehouseService.createWarehouse(warehouse);
        return Result.success();
    }

    @Operation(summary = "修改仓库")
    @PutMapping
    @PreAuthorize("hasAuthority('inventory:warehouse:edit')")
    public Result<Void> edit(@RequestBody Warehouse warehouse) {
        warehouseService.updateWarehouse(warehouse);
        return Result.success();
    }

    @Operation(summary = "删除仓库")
    @DeleteMapping("/{warehouseId}")
    @PreAuthorize("hasAuthority('inventory:warehouse:remove')")
    public Result<Void> remove(@PathVariable Long warehouseId) {
        warehouseService.removeById(warehouseId);
        return Result.success();
    }
}
