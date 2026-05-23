package com.erp.crm.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data @TableName("crm_opportunity")
public class CrmOpportunity {
    @TableId(type = IdType.AUTO) private Long opportunityId;
    private String opportunityName; private Long customerId;
    private BigDecimal expectedAmount; private Integer probability;
    private Integer stage; private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
