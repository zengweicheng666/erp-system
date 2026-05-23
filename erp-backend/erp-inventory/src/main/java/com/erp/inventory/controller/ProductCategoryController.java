package com.erp.inventory.controller;

import com.erp.common.result.Result;
import com.erp.inventory.entity.ProductCategory;
import com.erp.inventory.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品分类管理")
@RestController
@RequestMapping("/inventory/category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Operation(summary = "获取分类树")
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('inventory:category:list')")
    public Result<List<ProductCategory>> tree() {
        List<ProductCategory> categories = productCategoryService.listCategories();
        return Result.success(productCategoryService.buildTree(categories));
    }

    @Operation(summary = "新增分类")
    @PostMapping
    @PreAuthorize("hasAuthority('inventory:category:add')")
    public Result<Void> add(@RequestBody ProductCategory category) {
        productCategoryService.createCategory(category);
        return Result.success();
    }

    @Operation(summary = "修改分类")
    @PutMapping
    @PreAuthorize("hasAuthority('inventory:category:edit')")
    public Result<Void> edit(@RequestBody ProductCategory category) {
        productCategoryService.updateCategory(category);
        return Result.success();
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('inventory:category:remove')")
    public Result<Void> remove(@PathVariable Long categoryId) {
        productCategoryService.removeById(categoryId);
        return Result.success();
    }
}
