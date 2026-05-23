package com.erp.production.controller;
import com.erp.common.result.Result;
import com.erp.production.entity.ProductBom;
import com.erp.production.service.ProductBomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "BOM管理") @RestController @RequestMapping("/production/bom") @RequiredArgsConstructor
public class ProductBomController {
    private final ProductBomService productBomService;
    @GetMapping("/{productId}") public Result<List<ProductBom>> getByProduct(@PathVariable Long productId) { return Result.success(productBomService.getBomByProductId(productId)); }
    @PostMapping @PreAuthorize("hasAuthority('production:bom:add')") public Result<Void> add(@RequestBody ProductBom bom) { productBomService.save(bom); return Result.success(); }
    @DeleteMapping("/{id}") @PreAuthorize("hasAuthority('production:bom:remove')") public Result<Void> remove(@PathVariable Long id) { productBomService.removeById(id); return Result.success(); }
}
