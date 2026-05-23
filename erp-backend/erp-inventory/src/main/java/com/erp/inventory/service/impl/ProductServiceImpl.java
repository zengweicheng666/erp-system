package com.erp.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.inventory.dto.ProductQuery;
import com.erp.inventory.entity.Product;
import com.erp.inventory.mapper.ProductMapper;
import com.erp.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public PageResult<Product> listProducts(ProductQuery query) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getProductCode())) {
            wrapper.like(Product::getProductCode, query.getProductCode());
        }
        if (StringUtils.hasText(query.getProductName())) {
            wrapper.like(Product::getProductName, query.getProductName());
        }
        if (query.getCategoryId() != null) {
            wrapper.eq(Product::getCategoryId, query.getCategoryId());
        }
        if (query.getStatus() != null) {
            wrapper.eq(Product::getStatus, query.getStatus());
        }
        wrapper.orderByDesc(Product::getCreateTime);

        Page<Product> page = new Page<>(query.getPage(), query.getPageSize());
        Page<Product> result = page(page, wrapper);
        return PageResult.of(result.getRecords(), result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public boolean createProduct(Product product) {
        return save(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        return updateById(product);
    }
}
