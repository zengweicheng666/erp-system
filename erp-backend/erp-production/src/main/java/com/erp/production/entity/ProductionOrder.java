package com.erp.production.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data @TableName("pro_order")
public class ProductionOrder {
    @TableId(type = IdType.AUTO) private Long orderId;
    private String orderNo; private Long productId; private Integer quantity;
    private Integer producedQty; private LocalDateTime planStart;
    private LocalDateTime planEnd; private Integer status;
    private String remark;
    @TableField(fill = FieldFill.INSERT) private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE) private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT) private String createBy;
}
