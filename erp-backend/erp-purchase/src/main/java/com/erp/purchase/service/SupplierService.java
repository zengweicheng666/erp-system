package com.erp.purchase.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.common.result.PageResult;
import com.erp.purchase.dto.SupplierQuery;
import com.erp.purchase.entity.Supplier;
public interface SupplierService extends IService<Supplier> {
    PageResult<Supplier> listSuppliers(SupplierQuery query);
    boolean createSupplier(Supplier supplier);
    boolean updateSupplier(Supplier supplier);
}
