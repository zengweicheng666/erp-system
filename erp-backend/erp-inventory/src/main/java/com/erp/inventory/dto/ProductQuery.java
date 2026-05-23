package com.erp.inventory.dto;

import lombok.Data;

@Data
public class ProductQuery {
    private String productCode;
    private String productName;
    private Long categoryId;
    private Integer status;
    private Long page = 1L;
    private Long pageSize = 10L;
}
