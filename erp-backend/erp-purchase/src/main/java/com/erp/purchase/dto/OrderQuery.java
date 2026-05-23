package com.erp.purchase.dto;
import lombok.Data;
@Data
public class OrderQuery {
    private String orderNo; private Long supplierId; private Integer status;
    private Long page = 1L; private Long pageSize = 10L;
}
