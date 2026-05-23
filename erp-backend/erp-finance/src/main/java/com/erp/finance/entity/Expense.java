package com.erp.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fin_expense")
public class Expense {
    @TableId(type = IdType.AUTO)
    private Long expenseId;
    private String expenseNo;
    private LocalDateTime expenseDate;
    private String expenseType;
    private BigDecimal amount;
    private String department;
    private String operatorName;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
}
