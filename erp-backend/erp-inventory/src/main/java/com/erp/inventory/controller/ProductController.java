package com.erp.inventory.controller;

import com.erp.common.result.Result;
import com.erp.common.result.PageResult;
import com.erp.inventory.dto.ProductQuery;
import com.erp.inventory.entity.Product;
import com.erp.inventory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商品管理")
@RestController
@RequestMapping("/inventory/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "分页查询商品")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('inventory:product:list')")
    public Result<PageResult<Product>> list(ProductQuery query) {
        return Result.success(productService.listProducts(query));
    }

    @Operation(summary = "获取商品详情")
    @GetMapping("/{productId}")
    @PreAuthorize("hasAuthority('inventory:product:query')")
    public Result<Product> get(@PathVariable Long productId) {
        return Result.success(productService.getById(productId));
    }

    @Operation(summary = "新增商品")
    @PostMapping
    @PreAuthorize("hasAuthority('inventory:product:add')")
    public Result<Void> add(@RequestBody Product product) {
        productService.createProduct(product);
        return Result.success();
    }

    @Operation(summary = "修改商品")
    @PutMapping
    @PreAuthorize("hasAuthority('inventory:product:edit')")
    public Result<Void> edit(@RequestBody Product product) {
        productService.updateProduct(product);
        return Result.success();
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('inventory:product:remove')")
    public Result<Void> remove(@PathVariable Long productId) {
        productService.removeById(productId);
        return Result.success();
    }
}
