package com.erp.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fin_receipt")
public class Receipt {
    @TableId(type = IdType.AUTO)
    private Long receiptId;
    private String receiptNo;
    private Long customerId;
    private LocalDateTime receiptDate;
    private BigDecimal amount;
    private String paymentMethod;
    private String invoiceNo;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
}
