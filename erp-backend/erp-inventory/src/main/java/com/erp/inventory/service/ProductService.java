package com.erp.inventory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.inventory.dto.ProductQuery;
import com.erp.inventory.entity.Product;
import com.erp.common.result.PageResult;

public interface ProductService extends IService<Product> {
    PageResult<Product> listProducts(ProductQuery query);
    boolean createProduct(Product product);
    boolean updateProduct(Product product);
}
