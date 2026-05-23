package com.erp.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.inventory.entity.ProductCategory;
import com.erp.inventory.mapper.ProductCategoryMapper;
import com.erp.inventory.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Override
    public List<ProductCategory> listCategories() {
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(ProductCategory::getOrderNum);
        return list(wrapper);
    }

    @Override
    public List<ProductCategory> buildTree(List<ProductCategory> categories) {
        List<ProductCategory> tree = new ArrayList<>();
        List<ProductCategory> flat = categories.stream()
                .filter(c -> c.getCategoryId() != null)
                .collect(Collectors.toList());

        for (ProductCategory cat : flat) {
            if (cat.getParentId() == null || cat.getParentId() == 0) {
                tree.add(findChildren(cat, flat));
            }
        }
        return tree;
    }

    private ProductCategory findChildren(ProductCategory parent, List<ProductCategory> flat) {
        List<ProductCategory> children = new ArrayList<>();
        for (ProductCategory cat : flat) {
            if (parent.getCategoryId().equals(cat.getParentId())) {
                children.add(findChildren(cat, flat));
            }
        }
        parent.setChildren(children);
        return parent;
    }

    @Override
    public boolean createCategory(ProductCategory category) {
        return save(category);
    }

    @Override
    public boolean updateCategory(ProductCategory category) {
        return updateById(category);
    }
}
