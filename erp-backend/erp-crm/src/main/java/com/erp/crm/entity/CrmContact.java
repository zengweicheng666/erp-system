package com.erp.crm.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data @TableName("crm_contact")
public class CrmContact {
    @TableId(type = IdType.AUTO) private Long contactId;
    private String contactName; private Long customerId;
    private String phone; private String email; private String position;
    private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
