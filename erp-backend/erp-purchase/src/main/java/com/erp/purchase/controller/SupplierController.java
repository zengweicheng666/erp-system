package com.erp.purchase.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.SupplierQuery;
import com.erp.purchase.entity.Supplier;
import com.erp.purchase.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "供应商管理")
@RestController
@RequestMapping("/purchase/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    @Operation(summary = "分页查询供应商")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('purchase:supplier:list')")
    public Result<PageResult<Supplier>> list(SupplierQuery q) { return Result.success(supplierService.listSuppliers(q)); }
    @GetMapping("/{id}")
    public Result<Supplier> get(@PathVariable Long id) { return Result.success(supplierService.getById(id)); }
    @PostMapping
    @PreAuthorize("hasAuthority('purchase:supplier:add')")
    public Result<Void> add(@RequestBody Supplier s) { supplierService.createSupplier(s); return Result.success(); }
    @PutMapping
    @PreAuthorize("hasAuthority('purchase:supplier:edit')")
    public Result<Void> edit(@RequestBody Supplier s) { supplierService.updateSupplier(s); return Result.success(); }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('purchase:supplier:remove')")
    public Result<Void> remove(@PathVariable Long id) { supplierService.removeById(id); return Result.success(); }
}
