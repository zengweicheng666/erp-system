package com.erp.sales.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName("sal_order_item")
public class SalesOrderItem {
    @TableId(type = IdType.AUTO)
    private Long itemId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer deliveredQty;
    private String remark;
}
