package com.erp.sales.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sal_outbound")
public class SalesOutbound {
    @TableId(type = IdType.AUTO)
    private Long outboundId;
    private String outboundNo;
    private Long orderId;
    private String orderNo;
    private Long warehouseId;
    private LocalDateTime outboundDate;
    private String operator;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
}
