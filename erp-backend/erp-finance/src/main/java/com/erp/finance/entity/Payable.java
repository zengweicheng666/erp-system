package com.erp.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fin_payable")
public class Payable {
    @TableId(type = IdType.AUTO)
    private Long payableId;
    private String invoiceNo;
    private Long supplierId;
    private Long purchaseOrderId;
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
