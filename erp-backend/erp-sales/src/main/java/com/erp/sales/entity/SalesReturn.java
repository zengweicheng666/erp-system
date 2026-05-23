package com.erp.sales.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("sal_return")
public class SalesReturn {
    @TableId(type = IdType.AUTO)
    private Long returnId;
    private String returnNo;
    private Long customerId;
    private Long orderId;
    private LocalDateTime returnDate;
    private BigDecimal totalAmount;
    private Integer status;
    private String reason;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
}
