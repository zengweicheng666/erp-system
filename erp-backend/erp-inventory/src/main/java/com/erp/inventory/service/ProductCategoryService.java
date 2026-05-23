package com.erp.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.inventory.entity.ProductCategory;
import java.util.List;

public interface ProductCategoryService extends IService<ProductCategory> {
    List<ProductCategory> listCategories();
    List<ProductCategory> buildTree(List<ProductCategory> categories);
    boolean createCategory(ProductCategory category);
    boolean updateCategory(ProductCategory category);
}
