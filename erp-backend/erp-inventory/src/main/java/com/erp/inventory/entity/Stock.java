package com.erp.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("inv_stock")
public class Stock {
    @TableId(type = IdType.AUTO)
    private Long stockId;
    private Long productId;
    private Long warehouseId;
    private Integer quantity;
    private Integer lockedQuantity;
    private BigDecimal unitPrice;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
