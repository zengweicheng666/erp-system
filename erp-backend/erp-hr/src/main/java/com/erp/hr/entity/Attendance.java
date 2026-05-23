package com.erp.hr.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data @TableName("hr_attendance")
public class Attendance {
    @TableId(type = IdType.AUTO) private Long attendanceId;
    private Long employeeId; private LocalDate attendanceDate;
    private Integer attendanceType;
    private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
