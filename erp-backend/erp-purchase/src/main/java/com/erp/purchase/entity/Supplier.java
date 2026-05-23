package com.erp.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("pur_supplier")
public class Supplier {
    @TableId(type = IdType.AUTO)
    private Long supplierId;
    private String supplierCode;
    private String supplierName;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;
    private String taxId;
    private String bankName;
    private String bankAccount;
    private Integer status;
    private String remark;
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
