package com.erp.crm.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data @TableName("crm_lead")
public class CrmLead {
    @TableId(type = IdType.AUTO) private Long leadId;
    private String leadName; private String company;
    private String phone; private String email;
    private String source; private Integer status;
    private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
