package com.erp.hr.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data @TableName("hr_employee")
public class Employee {
    @TableId(type = IdType.AUTO) private Long employeeId;
    private String employeeNo; private String employeeName;
    private String gender; private LocalDate birthDate;
    private String phone; private String email;
    private Long deptId; private String position;
    private LocalDate hireDate; private Integer status;
    private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
