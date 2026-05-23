package com.erp.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("inv_product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Long productId;
    private String productCode;
    private String productName;
    private Long categoryId;
    private String unit;
    private String spec;
    private String model;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private Integer stockWarning;
    private String remark;
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableLogic
    private String delFlag;
}
