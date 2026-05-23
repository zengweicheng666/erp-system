package com.erp.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class StockOperateRequest {
    @NotNull(message = "商品ID不能为空")
    private Long productId;
    @NotNull(message = "仓库ID不能为空")
    private Long warehouseId;
    @NotNull(message = "数量不能为空")
    private Integer quantity;
    private BigDecimal unitPrice;
    private String remark;
    private String orderNo;
}
