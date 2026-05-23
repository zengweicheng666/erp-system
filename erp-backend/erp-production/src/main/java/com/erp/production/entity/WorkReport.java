package com.erp.production.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data @TableName("pro_work_report")
public class WorkReport {
    @TableId(type = IdType.AUTO) private Long reportId;
    private Long orderId; private Long processId;
    private Integer reportedQty; private Integer qualifiedQty;
    private Integer defectQty; private String worker; private LocalDateTime reportTime;
    private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
