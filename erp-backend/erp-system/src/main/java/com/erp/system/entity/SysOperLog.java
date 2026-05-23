package com.erp.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_oper_log")
public class SysOperLog {
    @TableId(type = IdType.AUTO)
    private Long operId;
    private String title;
    private Integer businessType;
    private String method;
    private String requestMethod;
    private String operUrl;
    private String operParam;
    private String jsonResult;
    private Integer status;
    private String errorMsg;
    private String operName;
    private String operIp;
    private Long costTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime operTime;
}
