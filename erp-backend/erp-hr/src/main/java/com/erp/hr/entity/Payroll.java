package com.erp.hr.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data @TableName("hr_payroll")
public class Payroll {
    @TableId(type = IdType.AUTO) private Long payrollId;
    private Long employeeId; private String period;
    private BigDecimal baseSalary; private BigDecimal bonus;
    private BigDecimal deduction; private BigDecimal netSalary;
    private Integer status;
    private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
