package com.erp.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("pur_inbound")
public class PurchaseInbound {
    @TableId(type = IdType.AUTO)
    private Long inboundId;
    private String inboundNo;
    private Long orderId;
    private String orderNo;
    private Long warehouseId;
    private LocalDateTime inboundDate;
    private String operator;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
}
