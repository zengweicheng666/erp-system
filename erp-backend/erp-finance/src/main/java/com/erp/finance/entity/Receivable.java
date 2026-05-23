package com.erp.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fin_receivable")
public class Receivable {
    @TableId(type = IdType.AUTO)
    private Long receivableId;
    private String invoiceNo;
    private Long customerId;
    private Long salesOrderId;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal unsettledAmount;
    private LocalDateTime dueDate;
    private Integer status;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
}
