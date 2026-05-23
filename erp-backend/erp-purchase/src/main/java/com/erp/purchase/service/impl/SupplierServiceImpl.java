package com.erp.purchase.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.SupplierQuery;
import com.erp.purchase.entity.Supplier;
import com.erp.purchase.mapper.SupplierMapper;
import com.erp.purchase.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {
    @Override
    public PageResult<Supplier> listSuppliers(SupplierQuery query) {
        LambdaQueryWrapper<Supplier> w = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getSupplierCode())) w.like(Supplier::getSupplierCode, query.getSupplierCode());
        if (StringUtils.hasText(query.getSupplierName())) w.like(Supplier::getSupplierName, query.getSupplierName());
        if (query.getStatus() != null) w.eq(Supplier::getStatus, query.getStatus());
        w.orderByDesc(Supplier::getCreateTime);
        Page<Supplier> p = page(new Page<>(query.getPage(), query.getPageSize()), w);
        return PageResult.of(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize());
    }
    @Override public boolean createSupplier(Supplier s) { return save(s); }
    @Override public boolean updateSupplier(Supplier s) { return updateById(s); }
}
