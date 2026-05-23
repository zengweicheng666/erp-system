package com.erp.crm.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data @TableName("crm_follow_up")
public class CrmFollowUp {
    @TableId(type = IdType.AUTO) private Long followUpId;
    private Long customerId; private Long leadId; private Long opportunityId;
    private String content; private String followUpType;
    private LocalDateTime followUpDate;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
