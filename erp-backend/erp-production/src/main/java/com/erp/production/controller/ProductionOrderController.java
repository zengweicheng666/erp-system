package com.erp.production.controller;
import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.production.entity.ProductionOrder;
import com.erp.production.service.ProductionOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Tag(name = "生产工单管理") @RestController @RequestMapping("/production/order") @RequiredArgsConstructor
public class ProductionOrderController {
    private final ProductionOrderService productionOrderService;
    @GetMapping("/list") @PreAuthorize("hasAuthority('production:order:list')") public Result<PageResult<ProductionOrder>> list(@RequestParam(defaultValue="1") Long page, @RequestParam(defaultValue="10") Long size) { return Result.success(productionOrderService.listOrders(page, size)); }
    @PostMapping @PreAuthorize("hasAuthority('production:order:add')") public Result<Void> add(@RequestBody ProductionOrder order) { productionOrderService.save(order); return Result.success(); }
    @PutMapping("/start/{id}") @PreAuthorize("hasAuthority('production:order:edit')") public Result<Void> start(@PathVariable Long id) { productionOrderService.startOrder(id); return Result.success(); }
    @PutMapping("/complete/{id}") @PreAuthorize("hasAuthority('production:order:edit')") public Result<Void> complete(@PathVariable Long id) { productionOrderService.completeOrder(id); return Result.success(); }
    @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('production:order:remove')") public Result<Void> remove(@PathVariable Long id) { productionOrderService.removeById(id); return Result.success(); }
}
