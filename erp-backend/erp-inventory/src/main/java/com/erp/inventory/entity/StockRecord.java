package com.erp.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("inv_stock_record")
public class StockRecord {
    @TableId(type = IdType.AUTO)
    private Long recordId;
    private Long productId;
    private Long warehouseId;
    private Integer quantity;
    private Integer recordType;
    private String orderNo;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String remark;
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
