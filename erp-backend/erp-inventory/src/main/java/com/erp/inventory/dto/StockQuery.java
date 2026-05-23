package com.erp.inventory.dto;

import lombok.Data;

@Data
public class StockQuery {
    private Long productId;
    private Long warehouseId;
    private Long page = 1L;
    private Long pageSize = 10L;
}
