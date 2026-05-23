package com.erp.purchase.dto;
import lombok.Data;
@Data
public class SupplierQuery {
    private String supplierCode; private String supplierName; private Integer status;
    private Long page = 1L; private Long pageSize = 10L;
}
