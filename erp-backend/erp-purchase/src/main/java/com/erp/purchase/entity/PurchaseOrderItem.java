package com.erp.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName("pur_order_item")
public class PurchaseOrderItem {
    @TableId(type = IdType.AUTO)
    private Long itemId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer receivedQty;
    private String remark;
}
